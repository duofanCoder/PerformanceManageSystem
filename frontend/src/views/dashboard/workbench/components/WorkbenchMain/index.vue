<template>
  <n-grid :item-responsive="true" :x-gap="16" :y-gap="16">
    <n-grid-item span="0:24 640:24 1024:16">
      <n-space :vertical="true" :size="16">
        <n-card title="新闻" :bordered="false" size="small" class="shadow-sm rounded-16px">
          <n-list>
            <n-list-item v-for="item in activity" :key="item.id">
              <template #prefix>
                <icon-custom-avatar class="text-48px" />
              </template>
              <n-thing :description="item.createTime">
                <template #header-extra>
                  {{ item.category.name }}
                </template>
                <template #header>
                  <div @click="showNewsModalClick(item)">
                    {{ item.name }}
                  </div>
                </template>
              </n-thing>
            </n-list-item>
          </n-list>
        </n-card>
      </n-space>
    </n-grid-item>
    <n-grid-item span="0:24 640:24 1024:8">
      <n-space :vertical="true" :size="16">
        <n-card title="创意" :bordered="false" size="small" class="shadow-sm rounded-16px">
          <icon-custom-banner class="text-400px text-primary" />
        </n-card>
      </n-space>
    </n-grid-item>
  </n-grid>
  <n-modal v-model:show="showModalRef">
    <n-card style="width: 600px" title="新闻" :bordered="false" size="huge" role="dialog" aria-modal="true">
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
          <n-input v-model:value="model.name" placeholder="请输入名称" disabled />
        </n-form-item>
        <n-form-item label="类型" path="category.id">
          <n-input v-model:value="model.category.name" disabled />
        </n-form-item>
        <n-form-item label="描述" path="description">
          <n-input v-model:value="model.description" disabled placeholder="请输入描述" type="textarea" />
        </n-form-item>
        <n-form-item label="发布时间" path="createTime">
          <n-input v-model:value="model.createTime" disabled placeholder="请输入描述" />
        </n-form-item>
      </n-form>
    </n-card>
  </n-modal>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { fetchQueryNews } from '@/service';

const activity = ref<Array<Dto.News>>([]);
const reload = () => {
  fetchQueryNews({}).then((res) => {
    if (res.data?.data !== undefined) {
      activity.value = res.data?.data;
    }
  });
};
const model = ref<Partial<Dto.News>>({});
const showModalRef = ref(false);
const showNewsModalClick = (item: Partial<Dto.News>) => {
  Object.assign(model.value, item);
  showModalRef.value = true;
};
onMounted(() => {
  reload();
});
</script>
<style scoped></style>
