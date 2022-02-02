package com.shopme.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {
	
@Autowired
private UserService service;
	
	@GetMapping("/users")
	private  String listFirstPage(Model model) {   
		List<User> listUsers =service.listAll();
    	model.addAttribute("listUsers", listUsers);
		return listByPage(1, model,"firstName","asc",null);
	}
	
	@GetMapping("users/page/{pageNum}")
	public String listByPage(
			@PathVariable(name="pageNum") int pageNum,Model model,
			@Param("sortField") String sortField,
			@Param("sortDir") String sortDir,
			@Param("keyWord") String keyword
			) {
		
		System.out.println("sortField:"+sortField);
		System.out.print("sortDir:"+sortDir);
		
		Page<User> page =service.listByPag(pageNum,sortField,sortDir,keyword);
		
		List<User> listUser = page.getContent();
		
		
		long startCount =(pageNum-1)*UserService.SIZA_PAGE_SIZE+1;
		long endCount = startCount + UserService.SIZA_PAGE_SIZE-1;
		
		if(endCount>page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc")? "desc":"asc";
		
		model.addAttribute("correntPage",pageNum);
		model.addAttribute("totalPege",page.getTotalPages());
    	model.addAttribute("startCount",startCount);
    	model.addAttribute("endCount",endCount);
    	model.addAttribute("totalItens",page.getTotalElements());
    	model.addAttribute("listUsers",listUser);
    	model.addAttribute("sortField",sortField);
    	model.addAttribute("sortDir",sortDir);
    	model.addAttribute("reverseSortDir",reverseSortDir);
    	model.addAttribute("keyword",keyword);
    	
    	return "users";

	}
	
	@GetMapping("/users/new")
	private String newUser(Model model) {
		User newUser = new User();
		List<Role> listRole =service.allAllRoles();
		newUser.setEnable(true);
		model.addAttribute("user", newUser);
		model.addAttribute("listRoles", listRole);
		model.addAttribute("pageTitle", "Create new User");
		return "form_user";
	}
	
	@PostMapping("/users/save")
	private String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saveUser = service.saveUser(user);
			String uploadDir ="user-photos/"+ saveUser.getUuid();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
		}else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			service.saveUser(user);
		}
		
		redirectAttributes.addFlashAttribute("message","The user has been saved sucessfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{uuid}")
	private String editUser(@PathVariable(name="uuid") Integer uuid,Model model, RedirectAttributes redirectAttributes) {
	
		try {
		User user = service.getUuid(uuid);
		List<Role> listRole =service.allAllRoles();
		model.addAttribute("listRoles", listRole);
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Edit User : ["+uuid+"]");
		return "form_user";
	
			} catch (UserNotFoundExeception ex) {
		redirectAttributes.addFlashAttribute("message"+ex.getMessage());
		return "redirect:/users";
		  }
  }
	
	@GetMapping("/users/delete/{uuid}")
	public String delete(@PathVariable(name="uuid") Integer uuid,Model model, RedirectAttributes redirectAttributes) {
		try {
			service.delete(uuid);
			redirectAttributes.addFlashAttribute("message","the user UUID:"+uuid+
					"has been deleted successflly");
			return "redirect:/users";
		
				} catch (UserNotFoundExeception ex) {
			redirectAttributes.addFlashAttribute("message"+ex.getMessage());
			return "redirect:/users";
	}
		}
	
	@GetMapping("/users/{uuid}/enabled/status")
	@ResponseBody
	public String updateUserEnabledStatus(@PathVariable("uuid") Integer uuid,
		 @PathVariable("status") boolean enabled , 
			RedirectAttributes redirectAttributes) {
		/*service.updateUserEnabledStatus(uuid, enabled);
		String status =  enabled? "enabled":"disabled";
		String mensage = "this user UUID"+uuid+"has been "+status;
		redirectAttributes.addFlashAttribute("message"+mensage);*/
		return "redirect:/users";
	}

}


