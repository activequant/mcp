Model to Code Platform
======================

This package is an easy to use code generator. It fits my personal use and might not fit yours. 
It's very lightweight (much more lightweight than AndroMDA or Sculptor) and easy to extend. 

It takes a project definition file, see sample.model, and generates hibernate Entities, DAO interface, DAO implementation, Service interface, Service implementation in a Spring autowired way. 

It uses velocity macro templates for those implementations, you can modify these templates or create new templates. 


Installation
------------

Download the distribution tar.gz and unzip it to a folder of your choice. 
Create an environment variable MCP_HOME that points to your mcp root directory (the one with the mcp command in it). 

Then add MCP_HOME folder to your $PATH variable, so that you can invoke 'mcp'. 

mcp will look for templates in MODEL_HOME/templates. 


Usage
-----

Assuming you have a model file, you invoke the mcp command:
Run 'mcp _model_file_' to run the code generator on it.  



