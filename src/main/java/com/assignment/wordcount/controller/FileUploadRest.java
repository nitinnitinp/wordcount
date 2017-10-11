package com.assignment.wordcount.controller;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.wordcount.exception.BaseException;
import com.assignment.wordcount.model.WordCount;
import com.assignment.wordcount.service.FileComputeService;
import com.assignment.wordcount.util.FileUitlity;
import com.assignment.wordcount.util.ConfigProperties;


@Controller
@RequestMapping("/fileuploadservice")
public class FileUploadRest {

	private final Logger _logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FileComputeService fileComputeService;
	
	@Autowired
	ConfigProperties properties;

	/**
	 * This method handle /computewordcount request for uploaded zip file 
	 * it expects zip file as a input and produce json output as a result
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/computewordcount", method=RequestMethod.POST,produces = "application/json", consumes = "application/zip")
	public @ResponseBody List<WordCount> compute(
			InputStream in) throws Exception{
		_logger.info("Handle request : /computewordcount");
		try {
			
			String destLocation = System.getProperty("user.home") + System.getProperty("file.separator") +
					properties.getFileUploadRootDir()	 + "-" + System.currentTimeMillis();
			FileUitlity.unzipFile(in, destLocation);
			return fileComputeService.computeWordCount(destLocation);
			//  Fork a separate thread to delete uploaded file before returning output, 
			/// since it is not a requirement for assignment I am not doing this .
		} catch (Exception e) {
			_logger.error("Error to perform request /computewordcount " + e);
			if(e instanceof BaseException) {
				throw e;
			} else if(e instanceof IllegalArgumentException) {
				throw new BaseException(HttpStatus.BAD_REQUEST, e.getMessage());
			}else {
				throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
	}

}


