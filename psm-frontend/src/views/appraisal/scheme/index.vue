<template>
  <n-card title="绩效考核" class="h-full shadow-sm rounded-16px">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>方案名称</div>
        <n-input v-model:value="queryKeyRef" placeholder="请输入关键字搜索"></n-input>
        <n-button type="primary" @click="clickEvent('search')">搜索</n-button>
      </n-space>
      <n-data-table
        ref="table"
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :row-key="(row) => row.id"
        @update:checked-row-keys="handleCheck"
      />
    </n-space>
  </n-card>
  <n-modal v-model:show="showModalRef">
    <n-card
      style="width: 75%"
      class="-top-40 max-h-75%"
      title="考核评价"
      :bordered="false"
      size="huge"
      role="dialog"
      aria-modal="true"
    >
      <n-space vertical>
        <div class="flex gap-4 items-center">
          <span class="w-12">被评人</span>
          <n-select v-model:value="model.audience" class="w-30" placeholder="请选择" :options="groupMemberOptions" />
        </div>

        <n-data-table ref="subTable" :columns="subColumns" :data="subData" :row-key="(row) => row.id" />

        <div class="w-full flex gap-5 justify-end">
          <n-button type="primary" @click="showModalRef = false">退出</n-button>
          <n-button type="primary" @click="clickEvent('submit')">提交</n-button>
        </div>
      </n-space>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, toRaw, unref, h } from 'vue';
import type { SelectOption } from 'naive-ui/lib/select/src/interface';
import { NButton, NInputNumber } from 'naive-ui';
import { fetchQueryScheme, fetchSaveRecord, fetchSaveScheme, fetchUpdateScheme } from '@/service';

const groupMemberOptions = ref<Array<SelectOption>>([]);
const queryKeyRef = ref('');
const model = ref<Partial<Dto.Record>>({});
const quotaScoreMap = new Map();

const showModalRef = ref(false);
const subData = ref<Array<Dto.Quota>>([]);

const columns = [
  {
    title: '名称',
    key: 'name',
  },
  {
    title: '描述',
    key: 'description',
  },
  {
    title: '考核方式',
    key: 'method.name',
  },
  {
    title: '添加时间',
    key: 'createTime',
  },
  {
    title: '操作',
    key: 'actions',
    render(row: any) {
      return h(
        NButton,
        {
          onClick: () => {
            showModalRef.value = true;
            groupMemberOptions.value = row.group.userList.map((v: Dto.User) => {
              return {
                label: v.name,
                value: v.id,
              };
            });
            subData.value = row.quotaList;
            model.value.schemeId = row.id;
          },
        },
        { default: () => '参评' }
      );
    },
  },
];
const data = ref<Array<Dto.Scheme>>([]);
const condition = reactive<Partial<Condition.Common>>({});

const reload = () => {
  fetchQueryScheme(condition).then((res) => {
    if (res.data?.data !== undefined) {
      data.value = res.data?.data;
    }
  });
};

const checkedRowKeysRef = ref([]);
const pagination = reactive({
  pageSize: 10,
  showSizePicker: true,
  pageSizes: [10, 20, 30],
  onUpdatePage: (page: number) => {
    condition.pageNum = page;
    reload();
  },
  onUpdatePageSize: (pageSize: number) => {
    condition.pageSize = pageSize;
    condition.pageNum = 0;
    pagination.pageSize = pageSize;
    reload();
  },
});
const handleCheck = (rowKeys: []) => {
  checkedRowKeysRef.value = rowKeys;
};

const submitClickEventRef = ref('');
const clickEvent = (type: string) => {
  switch (type) {
    case 'submit': {
      if (model.value.audience === undefined) {
        window.$message?.info('请先择被评人!');
        return;
      }
      model.value.scoreMap = quotaScoreMap;
      fetchSaveRecord(model.value).then((res) => {
        window.$message?.success('操作成功!');
        showModalRef.value = false;
      });
      break;
    }
    case 'search': {
      condition.name = queryKeyRef.value;
      reload();
      break;
    }
    case 'reload': {
      reload();
      break;
    }
    default:
      break;
  }
};

const submitClickEvent = () => {
  switch (submitClickEventRef.value) {
    case 'update': {
      fetchUpdateScheme(model.value).then(() => {
        reload();
        window.$message?.info('修改成功！');
      });

      break;
    }
    case 'add': {
      fetchSaveScheme(model.value).then(() => {
        reload();
        window.$message?.info('添加成功！');
      });
      break;
    }
    default: {
      break;
    }
  }
};
interface QuotaObject {
  id: number;
  score: number;
}

const subColumns = [
  {
    title: '名称',
    key: 'name',
  },
  {
    title: '描述',
    key: 'description',
  },
  {
    title: '分值',
    key: 'score',
  },
  {
    title: '权重',
    key: 'weight',
  },
  {
    title: '最低分',
    key: 'low',
    render() {
      return '0';
    },
  },
  {
    title: '最高分',
    key: 'high',
    render(row: Dto.Quota) {
      return row.score;
    },
  },

  {
    title: '评分',
    key: 'score',
    render(row: Dto.Quota) {
      return h(
        NInputNumber,
        {
          min: 0,
          max: row.score,
          onUpdateValue: (value: number | null) => {
            if (value !== null) {
              quotaScoreMap.set(row.id, row.weight * value);
            }
          },
        },
        {}
      );
    },
  },
];
onMounted(() => {
  reload();
});
</script>
<style scoped></style>
