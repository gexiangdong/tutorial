# Activiti 核心组件

## 关键对象

* Deployment：流程部署对象，部署一个流程时创建。
* ProcessDefinitions：流程定义，部署成功后自动创建。
* ProcessInstances：流程实例，启动流程时创建。 
* Task：任务，在Activiti中的Task仅指有角色参与的任务，即定义中的UserTask。 
* Execution：执行计划，流程实例和流程执行中的所有节点都是Execution，如UserTask、ServiceTask等。

## 服务接口

* ProcessEngine：流程引擎的抽象，通过它我们可以获得我们需要的一切服务。 
* RepositoryService：Activiti中每一个不同版本的业务流程的定义都需要使用一些定义文件，部署文件和支持数据(例如BPMN2.0 XML文件，表单定义文件，流程定义图像文件等)，这些文件都存储在Activiti内建的Repository中。RepositoryService提供了对 repository的存取服务。
* RuntimeService：在Activiti中，每当一个流程定义被启动一次之后，都会生成一个相应的流程对象实例。RuntimeService提供了启动流程、查询流程实例、设置获取流程实例变量等功能。此外它还提供了对流程部署，流程定义和流程实例的存取服务。
* TaskService: 在Activiti中业务流程定义中的每一个执行节点被称为一个Task，对流程中的数据存取，状态变更等操作均需要在Task中完成。TaskService提供了对用户Task 和Form相关的操作。它提供了运行时任务查询、领取、完成、删除以及变量设置等功能。 
  * 查询分配给用户或组的任务
  * 创建独立运行任务。这些任务与流程实例无关。
  * 手工设置任务的执行者，或者这些用户通过何种方式与任务关联。
  * 认领并完成一个任务。认领意味着一个人期望成为任务的执行者， 即这个用户会完成这个任务。完成意味着“做这个任务要求的事情”。 通常来说会有很多种处理形式。
* IdentityService: Activiti中内置了用户以及组管理的功能，必须使用这些用户和组的信息才能获取到相应的Task。IdentityService提供了对Activiti 系统中的用户和组的管理功能。它可以管理（创建，更新，删除，查询...）群组和用户。 请注意， Activiti执行时并没有对用户进行检查。 例如，任务可以分配给任何人，但是引擎不会校验系统中是否存在这个用户
* ManagementService: ManagementService提供了对Activiti流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，主要用于Activiti系统的日常维护。 ManagementService在使用Activiti的定制环境中基本上不会用到。 它可以查询数据库的表和表的元数据。另外，它提供了查询和管理异步操作的功能。 Activiti的异步操作用途很多，比如定时器，异步操作， 延迟暂停、激活，等等。后续，会讨论这些功能的更多细节。
* HistoryService: HistoryService用于获取正在运行或已经完成的流程实例的信息，与RuntimeService中获取的流程信息不同，历史信息包含已经持久化存储的永久信息，并已经被针对查询优化。HistoryService提供了Activiti引擎手机的所有历史数据。 在执行流程时，引擎会保存很多数据（根据配置），比如流程实例启动时间，任务的参与者， 完成任务的时间，每个流程实例的执行路径，等等。 这个服务主要通过查询功能来获得这些数据。
* FormService: Activiti 中的流程和状态 Task 均可以关联业务相关的数据。通过使用 Form Service 可以存取启动和完成任务所需的表单数据并且根据需要来渲染表单。FormService是一个可选服务。即使不使用它，Activiti也可以完美运行， 不会损失任何功能。这个服务提供了启动表单和任务表单两个概念。 启动表单会在流程实例启动之前展示给用户， 任务表单会在用户完成任务时展示。Activiti支持在BPMN 2.0流程定义中设置这些表单。 这个服务以一种简单的方式将数据暴露出来。再次重申，它时可选的， 表单也不一定要嵌入到流程定义中。
                                                                                                  

##