Model to Code Platform
======================

This package is an easy to use code generator. It fits my personal use and might not fit yours. 
It's very lightweight (much more lightweight than AndroMDA or Sculptor) and easy to extend. Several developer friends have told me they like it and that it creates beautiful code and is super easy to use. 

It takes a project definition file, see sample.model, and generates hibernate Entities, DAO interface, DAO implementation, Service interface, Service implementation in a Spring autowired way. 

It uses velocity macro templates for those implementations, you can modify these templates or create new templates. 

It is quite focused on Java, but other target language files can be created, too.


Installation
------------

Download the distribution tar.gz and unzip it to a folder of your choice. 
Create an environment variable MCP_HOME that points to your mcp root directory (the one with the mcp command in it). 

Then add MCP_HOME folder to your $PATH variable, so that you can invoke 'mcp'. 

mcp will look for templates in MCP_HOME/templates. 


Usage
-----

Assuming you have a model file, you invoke the mcp command:
Run 'mcp _model_file_' to run the code generator on it.  

Here's an example console output: 
```bash
ustaudinger@stormtrader:~/work/stormtrader/trunk$ mcp src/main/model/stormtrader.model
17:37:50.842 [main] INFO  com.stormdealers.mcp.YAMLToModel - Starting to process project object from file=src/main/model/stormtrader.model
17:37:51.099 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity AcctTargetPos
17:37:51.624 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IAcctTargetPosDao.java
17:37:51.689 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IAcctTargetPosService.java
17:37:51.757 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/AcctTargetPosService.java
17:37:51.854 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/AcctTargetPosDao.java
17:37:51.939 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/AcctTargetPos.java
17:37:51.942 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Creating folder src/main/java/com/stormtrader/model2/dto
17:37:51.943 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity MarketDataSubscription
17:37:51.974 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IMarketDataSubscriptionDao.java
17:37:52.027 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IMarketDataSubscriptionService.java
17:37:52.067 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/MarketDataSubscriptionService.java
17:37:52.115 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/MarketDataSubscriptionDao.java
17:37:52.176 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/MarketDataSubscription.java
17:37:52.179 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity OrderState
17:37:52.210 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IOrderStateDao.java
17:37:52.238 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IOrderStateService.java
17:37:52.276 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/OrderStateService.java
17:37:52.332 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/OrderStateDao.java
17:37:52.407 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/OrderState.java
17:37:52.412 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity Order
17:37:52.468 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IOrderDao.java
17:37:52.505 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IOrderService.java
17:37:52.541 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/OrderService.java
17:37:52.589 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/OrderDao.java
17:37:52.652 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/Order.java
17:37:52.656 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity AccountManager
17:37:52.687 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IAccountManagerDao.java
17:37:52.730 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IAccountManagerService.java
17:37:52.760 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/AccountManagerService.java
17:37:52.794 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/AccountManagerDao.java
17:37:52.834 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/AccountManager.java
17:37:52.837 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Rendering entity EquityShortSalesStats
17:37:52.870 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/dao/IEquityShortSalesStatsDao.java
17:37:52.904 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/interfaces/service/IEquityShortSalesStatsService.java
17:37:52.929 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/service/EquityShortSalesStatsService.java
17:37:52.964 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/dao/EquityShortSalesStatsDao.java
17:37:53.019 [main] INFO  com.stormdealers.mcp.VelocityRenderer - Writing file src/main/java/com/stormtrader/model2/entity/EquityShortSalesStats.java
ustaudinger@stormtrader:~/work/stormtrader/trunk$
```

### Model definitions

An example of a model file can be found in src/test/com/stormdealers/mcp/tests/sample.model

### Templates

All current templates are located in src/main/resources/templates - I imagine this to expand over the course of the next year or so.  


License
-------

This software is provided as is, with no guarantee of fitness for any purpose. It is distributed under the terms of the Apache Software License 2.0. 


Contributions
-------------

Contributions are welcome! I could imagine, we could end up with a template library that we can specify to use.   
