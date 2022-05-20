<template>
  <n-card title="用户管理" class="h-full shadow-sm rounded-16px">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>用户名称</div>
        <n-input v-model:value="condition.name" placeholder="请输入关键字搜索"></n-input>
        <div>职位</div>
        <n-select
          v-model:value="condition.positionId"
          class="w-30"
          placeholder="请选择"
          :options="positionOptionsCopy"
        />
        <div>部门</div>
        <n-select
          v-model:value="condition.departmentId"
          class="w-30"
          placeholder="请选择"
          :options="departmentOptionsCopy"
        />
        <n-button type="primary" @click="clickEvent('search')">搜索</n-button>
      </n-space>
      <n-space justify="start" align="center">
        <n-button type="success" @click="clickEvent('add')">添加</n-button>
        <n-button type="warning" @click="clickEvent('update')">修改</n-button>
        <n-button type="error" @click="clickEvent('remove')">删除</n-button>
        <n-button type="primary" @click="clickEvent('reload')">刷新</n-button>

        <n-button type="success" @click="clickEvent('password')">重置密码</n-button>
        <n-dropdown trigger="hover" :options="groupOptions" @select="handleGroupClick">
          <n-button type="primary">分组</n-button>
        </n-dropdown>
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
    <n-card style="width: 600px" title="用户管理" :bordered="false" size="huge" role="dialog" aria-modal="true">
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
          <n-input v-model:value="model.name" placeholder="请输入姓名" />
        </n-form-item>
        <n-form-item label="用户名" path="username">
          <n-input v-model:value="model.username" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="性别" path="gender">
          <n-space>
            <n-radio :checked="model.gender === false" value="false" @change="handleRadioChange"> 男</n-radio>
            <n-radio :checked="model.gender === true" value="true" @change="handleRadioChange"> 女</n-radio>
          </n-space>
        </n-form-item>
        <n-form-item label="手机号" path="mobile">
          <n-input v-model:value="model.mobile" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="职位" path="positionId">
          <n-select
            v-model:value="model.position.id"
            default-value="请选择"
            placeholder="请选择"
            :options="positionOptions"
          />
        </n-form-item>
        <n-form-item label="部门" path="departmentId">
          <n-select
            v-model:value="model.department.id"
            default-value="请选择"
            placeholder="请选择"
            :options="departmentOptions"
          />
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
import { NRadio } from 'naive-ui';
import {
  fetchQueryDepartment,
  fetchQueryGroup,
  fetchQueryPosition,
  fetchQueryUser,
  fetchRemoveUser,
  fetchAddUserGroup,
  fetchSaveUser,
  fetchUpdateUser,
  fetchResetUser,
} from '@/service';

const condition = reactive<Partial<Condition.User>>({});
const model = ref<Partial<Dto.User>>({});
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
    title: '用户名',
    key: 'username',
  },
  {
    title: '性别',
    key: 'gender',
    render(row: Dto.User) {
      return row.gender ? '女' : '男';
    },
  },
  {
    title: '部门',
    key: 'department.name',
  },
  {
    title: '职位',
    key: 'position.name',
  },
  {
    title: '添加时间',
    key: 'createTime',
  },
];
const data = ref<Array<Dto.User>>([]);

interface Select {
  label: string;
  value: number;
}

const positionOptions = ref<Array<Select>>([]);
const groupOptions = ref<Array<Select>>([]);
const departmentOptions = ref<Array<Select>>([]);
const positionOptionsCopy = ref<Array<Select>>(Array<Select>());
const departmentOptionsCopy = ref<Array<Select>>(Array<Select>());
const reload = () => {
  fetchQueryUser(toRaw(unref(condition))).then((res) => {
    data.value = res.data?.data;
  });
};

const optionLoad = () => {
  fetchQueryPosition({}).then((res) => {
    positionOptions.value = res.data?.data.map((v) => {
      return {
        label: v.name,
        value: v.id,
      };
    });
    positionOptionsCopy.value.push({
      label: '所有',
      value: 0,
    });
    res.data?.data.forEach((v) => {
      positionOptionsCopy.value.push({
        label: v.name,
        value: v.id,
      });
    });
  });
  fetchQueryDepartment({}).then((res) => {
    departmentOptions.value = res.data?.data.map((v) => {
      return {
        label: v.name,
        value: v.id,
      };
    });
    departmentOptionsCopy.value.push({
      label: '所有',
      value: 0,
    });
    res.data?.data.forEach((v) => {
      departmentOptionsCopy.value.push({
        label: v.name,
        value: v.id,
      });
    });
  });
  fetchQueryGroup({}).then((res) => {
    if (res.data?.data !== undefined) {
      groupOptions.value = res.data?.data.map((v) => {
        return {
          label: v.name,
          key: v.id,
        };
      });
    }
  });
};
onMounted(() => {
  reload();
  optionLoad();
});

const handleRadioChange = (e: Event) => {
  if ((e.target as HTMLInputElement).value === 'true') {
    model.value.gender = true;
  } else {
    model.value.gender = false;
  }
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
const handleGroupClick = (groupId: number) => {
  fetchAddUserGroup(groupId, checkedRowKeysRef.value).then((res) => {
    if (res.error == null) {
      window?.$message?.success('添加成功！');
    }
  });
};
const showModalRef = ref(false);
const submitClickEventRef = ref('');
const clickEvent = (type: string) => {
  const checkedRows = toRaw(unref(checkedRowKeysRef));
  switch (type) {
    case 'add': {
      model.value = {} as Partial<Dto.User>;
      model.value.position = {} as Partial<Dto.Position>;
      model.value.department = {} as Partial<Dto.Department>;
      showModalRef.value = true;
      submitClickEventRef.value = 'add';
      break;
    }
    case 'password': {
      if (checkedRows.length === 0) {
        window.$message?.info('请正确选择！');
      }
      checkedRows.forEach((item) => fetchResetUser(item));
      window.$message?.info('重置新密码为123456！');
      break;
    }
    case 'update': {
      if (checkedRows.length !== 1) {
        window.$message?.info('请正确选择！');
        return;
      }
      data.value.forEach((item) => {
        if (item.id === checkedRows[0]) {
          Object.assign(model.value, unref(toRaw(unref(item))));
        }
      });
      showModalRef.value = true;
      submitClickEventRef.value = 'update';
      break;
    }
    case 'remove': {
      fetchRemoveUser(checkedRowKeysRef.value).then((res) => {
        window.$message?.success('删除成功！');
        reload();
      });
      break;
    }
    case 'search': {
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
      fetchUpdateUser(model.value).then((res) => {
        reload();
        showModalRef.value = false;
        window.$message?.info('修改成功！');
      });
      break;
    }
    case 'add': {
      fetchSaveUser(model.value).then((res) => {
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
