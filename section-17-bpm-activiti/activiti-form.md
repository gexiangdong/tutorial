# FORM

FormService是可选的


User forms are not part of BPMN standard.

As you can see tags related to forms are located under extensionElements tag and are belonging to the specific namespace used with the prefix activiti.

extensionElements tag is part of BPMN standard (see section 8.2.3 of BPMN standard 2.0.2 or XML schema) and its purpose is to allow vendor specific extension to the standard (such as forms).



The new Activiti 6 UI uses a different way do display forms. It includes a Form Designer that enables designing forms by dragging and dropping fields. Forms are then referenced using the activiti:formKey attribute in start events and user tasks.


增加一个表单

```xml
<userTask id="task">
  <extensionElements>
    <activiti:formProperty id="room" />
    <activiti:formProperty id="duration" type="long"/>
    <activiti:formProperty id="speaker" variable="SpeakerName" writable="false" />
    <activiti:formProperty id="street" expression="#{address.street}" required="true" />
  </extensionElements>
</userTask>
```

* Form property room will be mapped to process variable room as a String
* Form property duration will be mapped to process variable duration as a java.lang.Long
* Form property speaker will be mapped to process variable SpeakerName. It will only be available in the TaskFormData object. If property speaker is submitted, an ActivitiException will be thrown. Analogue, with attribute readable="false", a property can be excluded from the FormData, but still be processed in the submit.
* Form property street will be mapped to Java bean property street in process variable address as a String. And required="true" will throw an exception during the submit if the property is not provided.

It’s also possible to provide type metadata as part of the FormData that is returned from methods StartFormData FormService.getStartFormData(String processDefinitionId) and TaskFormdata FormService.getTaskFormData(String taskId)

We support the following form property types:

* string (org.activiti.engine.impl.form.StringFormType
* long (org.activiti.engine.impl.form.LongFormType)
* enum (org.activiti.engine.impl.form.EnumFormType)
* date (org.activiti.engine.impl.form.DateFormType)
* boolean (org.activiti.engine.impl.form.BooleanFormType)

