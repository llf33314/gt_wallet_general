- 案例
#html页面时间转换
```
<span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>

```
#js文件调用
import { parseTime } from '../../../filters' //引入

parseTime(value, {y}-{m}-{d} {h}:{i});
