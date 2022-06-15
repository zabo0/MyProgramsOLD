package com.saboon.myprograms.Models.Subject

data class ModelSubjectProgramMainSection(
    val day: Int,
    val subjects: List<ModelSubject>,
    val subjectTimes: ArrayList<ModelSubjectTime>
)