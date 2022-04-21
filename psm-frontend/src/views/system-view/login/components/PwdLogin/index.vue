<template>
	<n-form ref="formRef" :model="model" size="large" :show-label="false">
		<n-form-item path="phone">
			<n-input v-model:value="model.phone" placeholder="请输入手机号码" />
		</n-form-item>
		<n-form-item path="pwd">
			<n-input v-model:value="model.pwd" type="password" show-password-on="click" placeholder="请输入密码" />
		</n-form-item>
		<n-space :vertical="true" :size="24">
			<div class="flex-y-center justify-between">
			</div>
			<n-button
				type="primary"
				size="large"
				:block="true"
				:round="true"
				:loading="auth.loginLoding"
				@click="handleSubmit"
			>
				确定
			</n-button>
		</n-space>
		<other-login />
	</n-form>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import type { FormInst } from "naive-ui";
import { EnumLoginModule } from "@/enum";
import { useAuthStore } from "@/store";
import { useRouterPush } from "@/composables";
import { OtherLogin } from "./components";

const auth = useAuthStore();
const { login } = useAuthStore();
const { toLoginModule } = useRouterPush();

const formRef = ref<(HTMLElement & FormInst) | null>(null);
const model = reactive({
	phone: "duofan",
	pwd: "123456"
});

function handleSubmit(e: MouseEvent) {
	if (!formRef.value) return;
	e.preventDefault();
	const { phone, pwd } = model;
	login(phone, pwd, "pwd");
}
</script>
<style scoped></style>
