package com.gns.user.services.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.Task;
import com.gns.user.services.repository.TaskRepository;
import com.gns.user.services.util.ExceptionHandlerUtil;

@Service
public class TaskServices {
	private static final Logger LOGGER = LogManager.getLogger(TaskServices.class);

	@Autowired
	TaskRepository taskRepository;

	public JSONObject createTasks(List<Task> tasks) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			taskRepository.saveAll(tasks);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Created Tasks Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

	public List<Task> getAllTasks(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Task> pagedResult = taskRepository.findAll(paging);
		return pagedResult.getContent();
	}

}
