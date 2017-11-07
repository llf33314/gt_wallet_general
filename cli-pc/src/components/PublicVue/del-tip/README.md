### 公共功能组件===删除提示
#### 调用方法：
```
  // vue
  <gt-del-tip :showTip.sync="dialogTip" :confirmFuc="delEmployee">
    <p>我是标题</p>
    <p>我是副标题</p>
  </gt-del-tip>

  // data() {} 数据 必须要一个控制弹窗 显示/隐藏的 变量 
   data() {
      dialogTip： true
   }

  // methods: {} 方法 可以传一个函数 点击确定后执行 会返回一个让弹框隐藏的函数 hide()
  delEmployee(hide) {
      console.log("需要执行业务");
      hide();                         调用 隐藏弹窗
    }

```
