<!-- null-data.vue组件 -->
<template>
  <div class="gt-show-phone">
    <div class="section section-device bg-gray">
    <div class="device device-iphone-8 device-silver">
      <div class="device-frame">
        <div class="device-content">
          <slot></slot>
        </div>
      </div>
      <div class="device-stripe"></div>
      <div class="device-header">
        <div class="device-sensors"></div>
      </div>
      <div class="device-btns"></div>
      <div class="device-power"></div>
      <div class="device-home"></div>
    </div>
    </div>
  </div>
</template>

<script>
  export default {

  }
</script>

<style  type="text/css" >
  @import './devices.css';
  .device-iphone-8 .device-stripe::after, .device-iphone-8 .device-stripe::before {
    width: 337px;
  }
  .device-iphone-8 .device-frame .device-content {
    border: 1px solid #e2e3e4;
    width: 310px;
    z-index: 1;
  }
</style>
