package com.example.aplikasigithubusernavigationdanapi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("followers")
    val followers: Int?,

    @field:SerializedName("following")
    val following: Int?,

    @field:SerializedName("followers_url")
    val followersUrl: String?,

    @field:SerializedName("following_url")
    val followingUrl: String?,

    @field:SerializedName("public_repos")
    val repository: Int?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("avatar_url")
    val avatar: String?
) : Parcelable

