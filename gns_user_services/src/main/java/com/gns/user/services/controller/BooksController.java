package com.gns.user.services.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.Book;
import com.gns.user.services.service.BookServices;
import com.gns.user.services.util.ExceptionHandlerUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/books")
public class BooksController {
	private static final Logger LOGGER = LogManager.getLogger(BooksController.class);

	@Autowired
	BookServices bookServices;

	@PutMapping(path = "createBooks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody List<Book> books) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = bookServices.createBooks(books);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("all")
	public ResponseEntity<String> getAllBooks(@RequestParam("start") int start, @RequestParam("end") int end) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(UserServicesConstants.RESULT, bookServices.getAllBooks(start, end));
			jsonObject.put(UserServicesConstants.STATUS_CODE, 200);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

}
