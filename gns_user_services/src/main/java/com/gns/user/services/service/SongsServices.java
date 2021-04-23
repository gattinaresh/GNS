
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
import com.gns.user.services.model.Song;
import com.gns.user.services.repository.SongRepository;
import com.gns.user.services.util.ExceptionHandlerUtil;

@Service
public class SongsServices {
	private static final Logger LOGGER = LogManager.getLogger(SongsServices.class);

	@Autowired
	SongRepository songRepository;

	public JSONObject createSongs(List<Song> songs) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			songRepository.saveAll(songs);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Created Songs Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

	public List<Song> getAllSongs(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Song> pagedResult = songRepository.findAll(paging);
		return pagedResult.getContent();
	}

}
