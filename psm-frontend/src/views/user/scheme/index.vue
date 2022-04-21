<template>
  <n-card title="考核成绩" class="h-full shadow-sm rounded-16px">
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
        <n-data-table ref="subTable" :columns="subColumns" :data="subData" :row-key="(row) => row.id" />
        <div class="w-full flex gap-5 justify-end py-3">
          <div class="text-base">总分：{{ sumScoreRef }}</div>
        </div>

        <div class="w-full flex gap-5 justify-end">
          <n-button type="primary" @click="showModalRef = false">关闭</n-button>
        </div>
      </n-space>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, h } from 'vue';
import { NButton } from 'naive-ui';
import { fetchSaveRecord, fetchSaveScheme, fetchUpdateScheme, fetchQueryRecord } from '@/service';

const queryKeyRef = ref('');
const model = ref<Partial<Dto.Record>>({});
const quotaScoreMap = new Map();
const sumScoreRef = ref(0);

const showModalRef = ref(false);
const subData = ref<Array<Dto.Quota>>([]);

const columns = [
  {
    title: '名称',
    key: 'scheme.name',
  },
  {
    title: '描述',
    key: 'scheme.description',
  },
  {
    title: '考核方式',
    key: 'scheme.method.name',
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
            subData.value = row.scheme.quotaList;
            const map = row.scoreMap;
            console.log(map);
            subData.value = subData.value.map((v) => {
              console.log(v);

              sumScoreRef.value += map[v.id];
              return { ...v, hasScore: map[v.id] };
            });
            console.log(subData.value);
            model.value.schemeId = row.id;
          },
        },
        { default: () => '查看成绩' }
      );
    },
  },
];
const data = ref<Array<Dto.Scheme>>([]);
const condition = reactive<Partial<Condition.Common>>({});

const reload = () => {
  fetchQueryRecord(condition).then((res) => {
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
        console.log(res);
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
    title: '得分',
    key: 'hasScore',
  },
];
onMounted(() => {
  reload();
});
</script>
<style scoped></style>
