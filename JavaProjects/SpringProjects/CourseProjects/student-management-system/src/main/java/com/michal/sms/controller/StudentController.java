package com.michal.sms.controller;

import com.michal.sms.dto.StudentDto;
import com.michal.sms.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class StudentController {

    private StudentService studentService;

    @GetMapping("/students")
    public String getStudentList(Model model){
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudent(Model model){
        //model to store data
        StudentDto studentDto = new StudentDto();
        model.addAttribute("student", studentDto);

        return "create_student";
    }

    @PostMapping("/students")
    public String addStudent(@Valid @ModelAttribute("student") StudentDto studentDto,
                             BindingResult result,
                             Model model){

        if(result.hasErrors()){
            model.addAttribute("student", studentDto);
            return "create_student";
        }


        studentService.createStudent(studentDto);

        return "redirect:/students";
    }

    //edit

    @GetMapping("/students/{studentId}/edit")
    public String editStudent(@PathVariable("studentId") Long studentId,
                              Model model){

        StudentDto studentDto = studentService.getStudentById(studentId);

        model.addAttribute("student", studentDto);

        return "edit_student";
    }

    @PostMapping("/students/{studentId}")
    public String updateStudent(@PathVariable("studentId") Long studentId,
                                @Valid @ModelAttribute("student") StudentDto studentDto,
                                BindingResult result,
                                Model model){

        if(result.hasErrors()){
            model.addAttribute("student", studentDto);
            return "edit_student";
        }

        studentDto.setId(studentId);
        studentService.updateStudent(studentDto);

        return "redirect:/students";
    }
    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") Long studentId){

        studentService.deleteStudentById(studentId);

        return "redirect:/students";
    }

    @GetMapping("/students/{studentId}/view")
    public String viewStudent(@PathVariable("studentId") Long studentId,
                              Model model){

        StudentDto studentDto = studentService.getStudentById(studentId);
        model.addAttribute("student", studentDto);

        return "view_student";
    }




}
