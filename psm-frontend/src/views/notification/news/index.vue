<template>
  <n-card title="新闻管理" class="h-full shadow-sm rounded-16px">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>新闻标题名称</div>
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
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :row-key="(row) => row.id"
        @update:checked-row-keys="handleCheck"
      />
    </n-space>
  </n-card>
  <n-modal v-model:show="showModalRef">
    <n-card style="width: 600px" title="新闻管理" :bordered="false" size="huge" role="dialog" aria-modal="true">
      <n-form
        ref="formRef"
        :model="model"
        label-placement="left"
        label-width="auto"
        require-mark-placement="right-hanging"
        :style="{
          maxWidth: '640px',
        }"
      >
        <n-form-item label="名称" path="name">
          <n-input v-model:value="model.name" placeholder="请输入名称" />
        </n-form-item>
        <n-form-item label="描述" path="description">
          <n-input v-model:value="model.description" placeholder="请输入描述" type="textarea" />
        </n-form-item>
        <n-form-item label="类型" path="categoryId">
          <n-select v-model:value="model.category.id" placeholder="请选择" :options="categoryOptions" />
        </n-form-item>
        <div style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="submitClickEvent"> 保存信息</n-button>
        </div>
      </n-form>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, toRaw, unref } from 'vue';
import { fetchQueryNews, fetchRemoveNews, fetchSaveNews, fetchUpdateNews, fetchQueryCategory } from '@/service';

const queryKeyRef = ref('');
const model = ref<Partial<Dto.News>>({});
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
    title: '分类',
    key: 'category.name',
  },
  {
    title: '描述',
    key: 'description',
  },
  {
    title: '添加时间',
    key: 'createTime',
  },
];

interface Select {
  label: string;
  value: number;
}

const data = ref<Array<Dto.News>>([]);
const condition = reactive<Partial<Condition.Common>>({});
const categoryOptions = ref<Array<Select>>([]);
const reload = () => {
  fetchQueryNews(condition).then((res) => {
    data.value = res.data?.data;
  });
};
onMounted(() => {
  reload();
  fetchQueryCategory(condition).then((res) => {
    categoryOptions.value = res.data?.data.map((v) => {
      return {
        label: v.name,
        value: v.id,
      };
    });
  });
});
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
const showModalRef = ref(false);
const submitClickEventRef = ref('');
const clickEvent = (type: string) => {
  const checkedRows = toRaw(unref(checkedRowKeysRef));
  switch (type) {
    case 'add': {
      model.value.name = '';
      model.value.description = '';
      model.value.category = {} as Dto.Category;
      model.value.category.id = null;
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
      showModalRef.value = true;
      submitClickEventRef.value = 'update';
      break;
    }
    case 'remove': {
      fetchRemoveNews(checkedRowKeysRef.value).then((res) => {
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
  switch (submitClickEventRef.value) {
    case 'update': {
      fetchUpdateNews(toRaw(unref(model))).then((res) => {
        reload();
        showModalRef.value = false;
        window.$message?.info('修改成功！');
      });
      break;
    }
    case 'add': {
      fetchSaveNews(model.value).then((res) => {
        reload();
        showModalRef.value = false;
        window.$message?.info('添加成功！');
      });
      break;
    }
    default: {
      break;
    }
  }
};
</script>
<style scoped></style>
