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
import com.gns.user.services.model.Remainder;
import com.gns.user.services.repository.RemainderRepository;
import com.gns.user.services.util.ExceptionHandlerUtil;

@Service
public class RemainderServices {
	private static final Logger LOGGER = LogManager.getLogger(RemainderServices.class);

	@Autowired
	RemainderRepository remainderRepository;

	public JSONObject createRemainders(List<Remainder> remainders) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			remainderRepository.saveAll(remainders);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Created Remainders Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

	public List<Remainder> getAllRemainders(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Remainder> pagedResult = remainderRepository.findAll(paging);
		return pagedResult.getContent();
	}

}
