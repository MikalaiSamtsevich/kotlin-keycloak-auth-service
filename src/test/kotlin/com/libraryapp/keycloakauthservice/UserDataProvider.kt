package com.libraryapp.keycloakauthservice

import com.libraryapp.keycloakauthservice.model.NewUserRecord
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

object UserDataProvider {
    val testUsers = listOf(
        NewUserRecord(
            username = "johndoe1",
            password = "P@ssw0rd123",
            passwordConfirm = "P@ssw0rd123",
            email = "johndoe1@example.com",
            firstname = "John",
            lastname = "Doe"
        ),
        NewUserRecord(
            username = "janedoe2",
            password = "P@ssw0rd456",
            passwordConfirm = "P@ssw0rd456",
            email = "janedoe2@example.com",
            firstname = "Jane",
            lastname = "Doe"
        ),
        NewUserRecord(
            username = "alexsmith3",
            password = "P@ssw0rd789",
            passwordConfirm = "P@ssw0rd789",
            email = "alexsmith3@example.com",
            firstname = "Alex",
            lastname = "Smith"
        ),
        NewUserRecord(
            username = "michaeljohnson4",
            password = "P@ssw0rd1011",
            passwordConfirm = "P@ssw0rd1011",
            email = "michaeljohnson4@example.com",
            firstname = "Michael",
            lastname = "Johnson"
        ),
        NewUserRecord(
            username = "emilydavis5",
            password = "P@ssw0rd1213",
            passwordConfirm = "P@ssw0rd1213",
            email = "emilydavis5@example.com",
            firstname = "Emily",
            lastname = "Davis"
        ),
        NewUserRecord(
            username = "davidthomas6",
            password = "P@ssw0rd1415",
            passwordConfirm = "P@ssw0rd1415",
            email = "davidthomas6@example.com",
            firstname = "David",
            lastname = "Thomas"
        ),
        NewUserRecord(
            username = "sarahwilson7",
            password = "P@ssw0rd1617",
            passwordConfirm = "P@ssw0rd1617",
            email = "sarahwilson7@example.com",
            firstname = "Sarah",
            lastname = "Wilson"
        ),
        NewUserRecord(
            username = "chrismartinez8",
            password = "P@ssw0rd1819",
            passwordConfirm = "P@ssw0rd1819",
            email = "chrismartinez8@example.com",
            firstname = "Chris",
            lastname = "Martinez"
        ),
        NewUserRecord(
            username = "lauragarcia9",
            password = "P@ssw0rd2021",
            passwordConfirm = "P@ssw0rd2021",
            email = "lauragarcia9@example.com",
            firstname = "Laura",
            lastname = "Garcia"
        ),
        NewUserRecord(
            username = "jamesanderson10",
            password = "P@ssw0rd2223",
            passwordConfirm = "P@ssw0rd2223",
            email = "jamesanderson10@example.com",
            firstname = "James",
            lastname = "Anderson"
        )
    )
    val testUsersWithDuplicates = listOf(
        NewUserRecord(
            username = "johndoe1",
            password = "P@ssw0rd123",
            passwordConfirm = "P@ssw0rd123",
            email = "johndoe1@example.com",
            firstname = "John",
            lastname = "Doe"
        ),
        NewUserRecord(
            username = "janedoe2",
            password = "P@ssw0rd456",
            passwordConfirm = "P@ssw0rd456",
            email = "janedoe2@example.com",
            firstname = "Jane",
            lastname = "Doe"
        ),
        NewUserRecord(
            username = "alexsmith3",
            password = "P@ssw0rd789",
            passwordConfirm = "P@ssw0rd789",
            email = "alexsmith3@example.com",
            firstname = "Alex",
            lastname = "Smith"
        ),
        NewUserRecord(                          // duplicate
            username = "alexsmith3",
            password = "P@ssw0rd789",
            passwordConfirm = "P@ssw0rd789",
            email = "alexsmith3@example.com",
            firstname = "Alex",
            lastname = "Smith"
        ),
        NewUserRecord(
            username = "michaeljohnson4",
            password = "P@ssw0rd1011",
            passwordConfirm = "P@ssw0rd1011",
            email = "michaeljohnson4@example.com",
            firstname = "Michael",
            lastname = "Johnson"
        ),
        NewUserRecord(
            username = "emilydavis5",
            password = "P@ssw0rd1213",
            passwordConfirm = "P@ssw0rd1213",
            email = "emilydavis5@example.com",
            firstname = "Emily",
            lastname = "Davis"
        ),
        NewUserRecord(
            username = "davidthomas6",
            password = "P@ssw0rd1415",
            passwordConfirm = "P@ssw0rd1415",
            email = "davidthomas6@example.com",
            firstname = "David",
            lastname = "Thomas"
        ),
        NewUserRecord(
            username = "sarahwilson7",
            password = "P@ssw0rd1617",
            passwordConfirm = "P@ssw0rd1617",
            email = "sarahwilson7@example.com",
            firstname = "Sarah",
            lastname = "Wilson"
        ),
        NewUserRecord(
            username = "chrismartinez8",
            password = "P@ssw0rd1819",
            passwordConfirm = "P@ssw0rd1819",
            email = "chrismartinez8@example.com",
            firstname = "Chris",
            lastname = "Martinez"
        ),
        NewUserRecord(
            username = "lauragarcia9",
            password = "P@ssw0rd2021",
            passwordConfirm = "P@ssw0rd2021",
            email = "lauragarcia9@example.com",
            firstname = "Laura",
            lastname = "Garcia"
        ),
        NewUserRecord(
            username = "jamesanderson10",
            password = "P@ssw0rd2223",
            passwordConfirm = "P@ssw0rd2223",
            email = "jamesanderson10@example.com",
            firstname = "James",
            lastname = "Anderson"
        )
    )
}
