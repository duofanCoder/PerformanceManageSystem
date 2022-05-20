<template>
  <n-card title="方案管理" class="h-full shadow-sm rounded-16px">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>方案名称</div>
        <n-input v-model:value="queryKeyRef" placeholder="请输入关键字搜索"></n-input>
        <n-button type="primary" @click="clickEvent('search')">搜索</n-button>
      </n-space>
      <n-space justify="start" align="center">
        <n-button type="success" @click="clickEvent('add')">添加</n-button>
        <n-button type="warning" @click="clickEvent('update')">修改</n-button>
        <n-button type="error" @click="clickEvent('remove')">删除</n-button>
        <n-button type="primary" @click="clickEvent('reload')">刷新</n-button>
      </n-space>

      <n-data-table
        ref="table"
        v-model:checked-row-keys="checkedRowKeysRef"
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
      class="-top-5 max-h-75%"
      title="方案管理"
      :bordered="false"
      size="huge"
      role="dialog"
      aria-modal="true"
    >
      <div class="w-full pl-20 mb-13">
        <n-steps :current="currentRef" :status="currentStatus">
          <n-step title="设备被评人" />
          <n-step title="添加评分指标" />
          <n-step title="设置评分方式" />
          <n-step title="完成" class="" />
        </n-steps>
      </div>

      <n-form
        v-if="currentRef == 1"
        ref="formRef2"
        :model="model"
        label-placement="left"
        label-width="auto"
        require-mark-placement="right-hanging"
      >
        <n-form-item label="名称" path="name">
          <n-input v-model:value="model.name" placeholder="请输入名称" />
        </n-form-item>

        <n-form-item label="被评分组" path="groupId">
          <n-select v-model:value="model.groupId" placeholder="请选择" :options="groupOptions" />
        </n-form-item>
        <n-form-item label="考核频率" path="frequency">
          <n-select v-model:value="model.frequency" placeholder="请选择" :options="frequencyOptions" />
        </n-form-item>
        <n-form-item label="描述" path="description">
          <n-input v-model:value="model.description" placeholder="请输入描述" type="textarea" />
        </n-form-item>
      </n-form>

      <div v-if="currentRef == 2">
        <n-data-table
          ref="subTable"
          v-model:checked-row-keys="subCheckedRowKeysRef"
          :columns="subColumns"
          :data="subData"
          :row-key="(row) => row.id"
          @update:checked-row-keys="subHandleCheck"
        />
      </div>

      <n-form
        v-if="currentRef == 3"
        ref="formRef3"
        :model="model"
        label-placement="left"
        label-width="auto"
        require-mark-placement="right-hanging"
      >
        <n-form-item label="评分方式" path="methodId">
          <n-select v-model:value="model.methodId" placeholder="请选择" :options="methodOptions" />
        </n-form-item>
      </n-form>

      <div v-if="currentRef == 4">
        <n-result status="success" title="成功">
          <template #footer>
            <n-button @click="showModalRef = false">关闭</n-button>
          </template>
        </n-result>
      </div>

      <div v-if="currentRef != 4" class="flex justify-end gap-5 mt-20">
        <div v-if="currentRef != 1" style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="currentRef -= 1"> 上一步</n-button>
        </div>
        <div v-if="currentRef != 3" style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="currentRef += 1"> 下一步</n-button>
        </div>

        <div v-if="currentRef == 3" style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="submitClickEvent"> 保存信息</n-button>
        </div>
      </div>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, toRaw, unref, watch } from 'vue';
import { NButton, NStep, NResult, NSteps } from 'naive-ui';
import type { StepsProps } from 'naive-ui/lib/steps/src/Steps';
import type { SelectOption } from 'naive-ui/lib/select/src/interface';
import {
  fetchQueryGroup,
  fetchQueryMethod,
  fetchQueryQuota,
  fetchQueryScheme,
  fetchRemoveScheme,
  fetchSaveScheme,
  fetchUpdateScheme,
} from '@/service';

const queryKeyRef = ref('');
const frequencyRef = ref('');
const model = ref<Partial<Dto.Scheme>>({});
const subCheckedRowKeysRef = ref<number[] | undefined>([]);
const columns = [
  {
    type: 'selection',
  },
  {
    title: '序号',
    key: 'id',
  },
  {
    title: '名称',
    key: 'name',
  },
  {
    title: '描述',
    key: 'description',
  },
  {
    title: '考核频率',
    key: 'frequency',
  },
  {
    title: '添加时间',
    key: 'createTime',
  },
];
const currentRef = ref<number>(1);
const data = ref<Array<Dto.Scheme>>([]);
const condition = reactive<Partial<Condition.Common>>({});
const checkedRowKeysRef = ref([]);

const reload = () => {
  fetchQueryScheme(condition).then((res) => {
    if (res.data?.data !== undefined) {
      data.value = res.data?.data;
    }
    checkedRowKeysRef.value = [];
    subCheckedRowKeysRef.value = [];
  });
};

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

//  modal table 配置
const subData = ref<Array<Dto.Quota>>([]);
const subColumns = [
  {
    type: 'selection',
  },
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
];

const subHandleCheck = (rowKeys: []) => {
  subCheckedRowKeysRef.value = rowKeys;
};

const showModalRef = ref(false);
const submitClickEventRef = ref('');
const clickEvent = (type: string) => {
  const checkedRows = toRaw(unref(checkedRowKeysRef));

  switch (type) {
    case 'add': {
      model.value.name = '';
      model.value.description = '';
      model.value.id = undefined;
      subCheckedRowKeysRef.value = [];
      showModalRef.value = true;
      submitClickEventRef.value = 'add';
      break;
    }
    case 'update': {
      if (checkedRows.length !== 1) {
        window.$message?.info('请正确选择！');
        return;
      }

      data.value.forEach((item) => {
        if (item.id === checkedRows[0]) {
          Object.assign(model.value, toRaw(unref(item)));
        }
      });
      if (model.value.quotaIds !== undefined) {
        subCheckedRowKeysRef.value = model.value.quotaIds;
      }

      showModalRef.value = true;
      submitClickEventRef.value = 'update';
      break;
    }
    case 'remove': {
      fetchRemoveScheme(checkedRowKeysRef.value).then((_res) => {
        window.$message?.success('删除成功！');
        reload();
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
  const subCheckedRows = toRaw(unref(subCheckedRowKeysRef));
  switch (submitClickEventRef.value) {
    case 'update': {
      model.value.quotaIds = subCheckedRows;
      if (model.value.frequency != undefined) {
        frequencyRef.value = model.value.frequency;
      }
      console.log(model.value);

      fetchUpdateScheme(toRaw(unref(model))).then(() => {
        reload();
        window.$message?.info('修改成功！');
        currentRef.value += 1;
      });
      break;
    }
    case 'add': {
      model.value.quotaIds = subCheckedRowKeysRef.value;
      fetchSaveScheme(model.value).then(() => {
        reload();
        window.$message?.info('添加成功！');
        currentRef.value += 1;
      });
      break;
    }
    default: {
      break;
    }
  }
};

// 步骤条
const currentStatus = ref<StepsProps['status']>('process');

const groupOptions = ref<Array<SelectOption>>([]);

const methodOptions = ref<Array<SelectOption>>([]);

onMounted(() => {
  fetchQueryMethod({}).then((res) => {
    if (res.data?.data !== undefined) {
      methodOptions.value = res.data?.data.map((v) => {
        return {
          label: v.name,
          value: v.id,
        };
      });
    }
  });

  fetchQueryGroup({}).then((res) => {
    if (res.data?.data !== undefined) {
      groupOptions.value = res.data?.data.map((v) => {
        return {
          label: v.name,
          value: v.id,
        };
      });
    }
  });
  fetchQueryQuota({}).then((res) => {
    if (res.data?.data !== undefined) {
      subData.value = res.data?.data;
    }
  });
  reload();
});
const frequencyOptions = [
  {
    label: '每日',
    value: '每日',
  },
  {
    label: '每周',
    value: '每周',
  },
  {
    label: '每月',
    value: '每月',
  },
  {
    label: '每年',
    value: '每年',
  },
];

watch(showModalRef, (newValue) => {
  if (newValue === true) {
    currentRef.value = 1;
  }
});
</script>
<style scoped></style>
