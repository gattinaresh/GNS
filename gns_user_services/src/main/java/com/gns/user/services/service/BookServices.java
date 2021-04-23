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
import com.gns.user.services.model.Book;
import com.gns.user.services.repository.BookRepository;
import com.gns.user.services.util.ExceptionHandlerUtil;

@Service
public class BookServices {
	
	private static final Logger LOGGER = LogManager.getLogger(BookServices.class);
	@Autowired
	BookRepository bookRepository;

	public JSONObject createBooks(List<Book> books) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			bookRepository.saveAll(books);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Created Books Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

	public List<Book> getAllBooks(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Book> pagedResult = bookRepository.findAll(paging);
		return pagedResult.getContent();
	}


}
