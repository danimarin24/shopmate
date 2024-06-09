package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.utilsEntities.UserActionResponseEntity
import com.example.shopmate_app.domain.usecases.user.AddUserUseCase
import com.example.shopmate_app.domain.usecases.user.AddUserWithImageUseCase
import com.example.shopmate_app.domain.usecases.user.GetGoogleSubByGoogleTokenUseCase
import com.example.shopmate_app.domain.usecases.user.GetIfIsFollowingThisUserUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByEmailUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByGoogleSubUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByIdUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByUsernameUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsernameGeneratedUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersFollowedsUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersFollowersUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersUseCase
import com.example.shopmate_app.domain.usecases.user.PostFollowUnfollowThisUserUseCase
import com.example.shopmate_app.domain.usecases.user.PutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getGoogleSubByGoogleTokenUseCase : GetGoogleSubByGoogleTokenUseCase,
    private val getUsernameGeneratedUseCase : GetUsernameGeneratedUseCase,
    private val getUserByEmailUseCase : GetUserByEmailUseCase,
    private val getUserByUsernameUseCase : GetUserByUsernameUseCase,
    private val getUserByIdUseCase : GetUserByIdUseCase,
    private val getUsersFollowedsUseCase: GetUsersFollowedsUseCase,
    private val getUsersFollowersUseCase: GetUsersFollowersUseCase,
    private val getIfIsFollowingThisUserUseCase: GetIfIsFollowingThisUserUseCase,
    private val postFollowUnfollowThisUserUseCase: PostFollowUnfollowThisUserUseCase,
    private val getUserByGoogleSubUseCase : GetUserByGoogleSubUseCase,
    private val addUserUseCase : AddUserUseCase,
    private val addUserWithImageUseCase: AddUserWithImageUseCase,
    private val putUserUseCase : PutUserUseCase,
) : ViewModel() {

    val userEntity = MutableLiveData<UserEntity?>()
    val usernameGenerated = MutableLiveData<String?>()
    val googleSub = MutableLiveData<String?>()


    private val _usersFolloweds = MutableLiveData<List<UserEntity>>()
    val usersFolloweds: LiveData<List<UserEntity>> get() = _usersFolloweds

    private val _usersFollowers = MutableLiveData<List<UserEntity>>()
    val usersFollowers: LiveData<List<UserEntity>> get() = _usersFollowers

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> get() = _isFollowing

    private val _actionResponse = MutableLiveData<UserActionResponseEntity>()
    val actionResponse: LiveData<UserActionResponseEntity> get() = _actionResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getGoogleSub(googleToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getGoogleSubByGoogleTokenUseCase(googleToken)

            if (result != null) {
                googleSub.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun getUserByGoogleSub(googleSub: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getUserByGoogleSubUseCase(googleSub)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getUserByUsernameUseCase(username)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun getUserByUserId(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getUserByIdUseCase(userId)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun fetchUserFolloweds(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val usersFolloweds = getUsersFollowedsUseCase(userId)
                _usersFolloweds.value = usersFolloweds
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserFollowers(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val usersFollowers = getUsersFollowersUseCase(userId)
                _usersFollowers.value = usersFollowers
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchIfIsFollowing(userId: Int, userActionId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isFollowing = getIfIsFollowingThisUserUseCase(userId, userActionId)
                _isFollowing.value = isFollowing
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun followUnfollowAction(userId: Int, userActionId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val actionResponse = postFollowUnfollowThisUserUseCase(userId, userActionId)
                _actionResponse.value = actionResponse
                fetchIfIsFollowing(userId, userActionId)
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getUserByEmailUseCase(email)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun generateUsername(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getUsernameGeneratedUseCase(name)

            if (result != null) {
                usernameGenerated.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = addUserUseCase(user)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun addUserWithImage(user: UserEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = addUserWithImageUseCase(user)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }

    fun putUser(user: UserEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = putUserUseCase(user)

            if (result != null) {
                userEntity.postValue(result)
                _isLoading.value = false
            }
        }
    }
}