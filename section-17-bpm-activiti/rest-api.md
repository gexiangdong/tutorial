# REST API

## 列出所有任务

``` bash
curl http://localhost:8080/runtime/tasks
```


## 创建新 hireProcess 流程

```bash
curl  -H "Content-Type: application/json" -d '{"processDefinitionKey":"hireProcess", "variables": [ {"name":"applicantName", "value":"waytt"}, {"name":"email", "value":"waytt@westworld.com"}, {"name":"phoneNumber", "value":"13312344321"} ]}' http://localhost:8080/runtime/process-instances

```
