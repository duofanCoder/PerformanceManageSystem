<template>
  <n-card title="个人信息" class="h-full shadow-sm rounded-16px">
    <n-space vertical justify="center">
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">姓名</div>
        <n-input v-model:value="model.name" class="w-auto w-30"></n-input>
      </div>
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">手机号</div>
        <n-input v-model:value="model.mobile" class="w-auto w-30"></n-input>
      </div>
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">性别</div>
        <div class="flex gap-5">
          <n-radio :checked="model.gender" :value="1" name="basic-demo" @change="model.gender = true"> 男 </n-radio>
          <n-radio :checked="model.gender == false" :value="0" name="basic-demo" @change="model.gender = false">
            女
          </n-radio>
        </div>
      </div>
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">部门</div>
        <n-input disabled :value="model.department.name" class="w-auto w-30"></n-input>
      </div>
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">职位</div>
        <n-input disabled :value="model.position.name" class="w-auto w-30"></n-input>
      </div>
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">角色</div>
        <n-input v-model:value="model.role" disabled class="w-auto w-30"></n-input>
      </div>
      <!--
      <div class="flex gap-5 w-100 items-center">
        <div class="w-20">密码</div>
        <n-input v-model:value="" class="w-auto w-30"></n-input>
      </div> -->
      <n-button type="primary" @click="saveUser">保存</n-button>
    </n-space>
  </n-card>
</template>
<script setup lang="ts">
import { reactive } from 'vue';
import { NRadio } from 'naive-ui';
import { useAuthStore } from '@/store';
import { fetchUpdateProfile } from '@/service';

interface UserData {
  name: string;
  gender: boolean;
  mobile: string;
}

const store = useAuthStore();
const model = reactive<Auth.UserInfo>(store.userInfo);
const saveUser = () => {
  fetchUpdateProfile({ ...model }).then((res) => {
    store.resetAuthStore();
  });
};
</script>
<style scoped></style>
