/**
 * @author pulem3t
 */
package org.pulem3t.tasks.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.pulem3t.tasks.entry.Task;
import org.pulem3t.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	private Logger logger = Logger.getLogger("TaskController");

	@RequestMapping(value="/addTask", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String addTask(@RequestBody String json) {
		
		JSONObject o = new JSONObject();
		String id = "";
		try {
			id = taskService.addTask(json);
			logger.info("TASKS: Added task with id = " + id);
			o.put("id", id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("id", id);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/delTask/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String delTask(@PathVariable("id") String id) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("TASK: Deleting task with id = " + id);
			taskService.delTask(id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/updateTask", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String updateTask(@RequestBody String json) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("TASKS: Updating task with id = " + new JSONObject(json).getString("id"));
			taskService.updateTask(json);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getLastAddedTasks", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getLastAddedTasks() {

		JSONObject o = new JSONObject();
		try {
			List<Task> taskList = taskService.getLastAddedTasks();
			o.put("items", taskList);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("items", new ArrayList<Task>());
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getTask/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getTask(@PathVariable("id") String id) {

		JSONObject o = new JSONObject();
		try {
			Task task = taskService.getTask(id);
			JSONObject t = new JSONObject();
			t.put("id", task.getId());
			t.put("prefix", task.getPrefix());
			t.put("title", task.getTitle());
			t.put("authorId", task.getAuthor().getId());
			t.put("parentTaskId", task.getParentTask().getId());
			t.put("performerId", task.getPerformer().getId());
			t.put("description", task.getDescription());
			t.put("status", task.getStatus());
			t.put("priority", task.getPriority());
			t.put("createDate", task.getCreateDate());
			t.put("lastmodDate", task.getLastmodDate());
			t.put("deadLine", task.getDeadLine());
			o.put("task", t);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getTasks", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getTasks() {

		JSONObject o = new JSONObject();
		try {
			List<Task> taskList = taskService.getTasks();
			o.put("items", taskList);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
	
	@RequestMapping(value="/setTaskPriority", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String setTaskPriority(String id, int priority) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("TASKS: Setting priority for task with id = " + id);
			taskService.setTaskPriority(id, priority);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
	
	@RequestMapping(value="/setTaskStatus", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String setTaskStatus(String id, int status) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("TASKS: Setting status task with id = " + id);
			taskService.setTaskStatus(id, status);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
}
