<template>
  <n-card :bordered="false" class="rounded-16px shadow-sm">
    <div class="flex-y-center justify-between">
      <div class="flex-y-center">
        <icon-custom-avatar class="text-70px" />
        <div class="pl-12px">
          <h3 class="text-18px font-semibold">早安，{{ auth.userInfo.name }}, 今天又是充满活力的一天！</h3>
          <p class="leading-30px text-[#999]">今日多云转晴，20℃ - 25℃！</p>
        </div>
      </div>
    </div>
  </n-card>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/store';

const auth = useAuthStore();
</script>
<style scoped></style>
