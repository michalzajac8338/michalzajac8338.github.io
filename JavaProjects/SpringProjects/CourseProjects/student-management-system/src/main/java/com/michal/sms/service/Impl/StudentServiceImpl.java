package com.michal.sms.service.Impl;

import com.michal.sms.dto.StudentDto;
import com.michal.sms.entity.Student;
import com.michal.sms.mapper.StudentMapper;
import com.michal.sms.repository.StudentRepository;
import com.michal.sms.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    @Override
    public List<StudentDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        List<StudentDto> studentsDto = students.stream().map(StudentMapper::mapToStudentDto).toList();

        return studentsDto;
    }

    @Override
    public void createStudent(StudentDto studentDto) {

        Student student = StudentMapper.mapToStudent(studentDto);
        studentRepository.save(student);

    }

    @Override
    public StudentDto getStudentById(Long studentId) {

        Student student = studentRepository.findById(studentId).get();

        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public void updateStudent(StudentDto studentDto) {
//        Student student = studentRepository.findById(studentDto.getId()).get();
        Student student = StudentMapper.mapToStudent(studentDto);
        studentRepository.save(student);

    }

    @Override
    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }


}
