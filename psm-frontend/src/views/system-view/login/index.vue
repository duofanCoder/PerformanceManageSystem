<template>
  <div class="relative flex-center wh-full h-screen" :style="{ backgroundColor: bgColor }">
    <dark-mode-switch
      :dark="theme.darkMode"
      class="absolute left-48px top-24px z-3 text-20px"
      @update:dark="theme.setDarkMode"
    />
    <n-card :bordered="false" size="large" class="z-4 !w-auto rounded-20px shadow-sm">
      <div class="w-360px">
        <header class="flex-y-center justify-between">
          <n-gradient-text type="primary" :size="28">{{ title }}</n-gradient-text>
        </header>
        <main class="pt-24px">
          <h3 class="text-18px text-primary font-medium">{{ activeModule.label }}</h3>
          <div class="pt-24px">
            <transition name="fade-slide" mode="out-in" appear>
              <component :is="activeModule.component" />
            </transition>
          </div>
        </main>
      </div>
    </n-card>
    <login-bg :theme-color="bgThemeColor" />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import type { Component } from 'vue';
import { EnumLoginModule } from '@/enum';
import { useThemeStore } from '@/store';
import { useAppInfo } from '@/composables';
import { getColorPalette, mixColor } from '@/utils';
import { LoginBg, PwdLogin } from './components';

interface Props {
  /** 登录模块分类 */
  module: EnumType.LoginModuleKey;
}

interface LoginModule {
  key: EnumType.LoginModuleKey;
  label: EnumLoginModule;
  component: Component;
}

const props = defineProps<Props>();

const theme = useThemeStore();
const { title } = useAppInfo();

const modules: LoginModule[] = [{ key: 'pwd-login', label: EnumLoginModule['pwd-login'], component: PwdLogin }];

const activeModule = computed(() => {
  const active: LoginModule = { ...modules[0] };
  const findItem = modules.find((item) => item.key === props.module);
  if (findItem) {
    Object.assign(active, findItem);
  }
  return active;
});

const bgThemeColor = computed(() => (theme.darkMode ? getColorPalette(theme.themeColor, 7) : theme.themeColor));

const bgColor = computed(() => {
  const COLOR_WHITE = '#ffffff';
  const ratio = theme.darkMode ? 0.5 : 0.2;
  return mixColor(COLOR_WHITE, theme.themeColor, ratio);
});
</script>
<style scoped></style>
