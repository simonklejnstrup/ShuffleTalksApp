package com.example.shuffletalksapp.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.model.*
import com.example.shuffletalksapp.ui.post.PostItemUIModel
import com.example.shuffletalksapp.util.Constants
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

object Api {

    // GET //

    // get post
    fun getOnePost(postId: String): Post {
        return posts.find { post -> post.postId == postId }!!
    }

    // get all posts
    fun getAllPosts(): MutableList<Post> {
        return posts
    }

    // get user
    fun getOneUser(userId: String): User {
        return users.find { user -> user.userId == userId }!!
    }

    fun getUserByUsername(username: String): User? {
        return users.find { user -> user.username.equals(username, ignoreCase = true) }


    }


    // POST //

    // create post
    @RequiresApi(Build.VERSION_CODES.O)
    fun createPost(content: String, username: String) {
        val user = getUserByUsername(username)

        if (user != null) {
            posts.add(
                0,
                Post(
                    UUID.randomUUID().toString(),
                    mutableListOf(
                        Comment(
                            user.userId,
                            user.username,
                            content,
                            UUID.randomUUID().toString(),
                            ArrayList<Like>(),
                            LocalDate.now().toString(),
                            LocalDate.now().toString(),
                            ArrayList<Quote>()
                        )
                    ) as ArrayList
                )
            )
            user.postcount += 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createComment(content: String, username: String, postId: String, quotes: Array<Quote>?) {
        val user = getUserByUsername(username)
        val post = getOnePost(postId)

        if (user != null) {
            post.comments.add(
                Comment(
                    user.userId,
                    username,
                    content,
                    UUID.randomUUID().toString(),
                    ArrayList<Like>(),
                    LocalDate.now().toString(),
                    LocalDate.now().toString(),
                    (quotes?.toMutableList() ?: ArrayList<Quote>()) as ArrayList<Quote>
                )
            )
            user.postcount += 1

        }
    }

    // post user
    fun createUser(user: User): String {

        val userByUsername = users.find { _user -> _user.username.equals(user.username, ignoreCase = true) }

        if (user.username.equals(userByUsername?.username, ignoreCase = true)) {
            return Constants.USERNAME_ALREADY_IN_USE_STRING
        }

        val userByEmail = users.find { _user -> _user.email.equals(user.email, ignoreCase = true) }

        if (user.email.equals(userByEmail?.email, ignoreCase = true)) {
            return Constants.EMAIL_ALREADY_IN_USE_STRING
        }

        users.add(user)

        return Constants.SUCCES_STRING
    }

    // Update

    fun updateUser(userId: String, newFirstname: String, newLastname: String, newEmail: String) {
        val updatedUser = getOneUser(userId)
        updatedUser.firstname = newFirstname
        updatedUser.lastname = newLastname
        updatedUser.email = newEmail

    }

    fun updateComment(newContent: String, commentId: String, postId: String) {
        val post = posts.find { post -> post.postId == postId }
        var comment = post?.comments?.find { comment -> comment.commentId == commentId }

        comment?.content = newContent

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateLikes(commentId: String, postId: String, _like: Like): Int {
        val post = posts.find { post -> post.postId == postId }

        if (post != null) {
            val comment = post.comments.find { comment -> comment.commentId == commentId }

            if (comment != null) {

                val isLikedByCurrentUser = comment.likes.any { like -> like.userId == _like.userId}
                if (isLikedByCurrentUser) {
                    comment.likes.removeIf { it.userId == _like.userId }
                } else {
                    comment.likes.add(_like)
                    println(comment.likes)
                }

                return comment.likes.size

            }

        }
        return -1
    }


    var users = mutableListOf<User>(
        User(
            "628e3c0ef24148cf460a1671",
            "Sp??rgeS??ren",
            "2022-05-25",
            "S??ren",
            "Mikkelsen",
            "Kbh",
            "sj@mail.dk",
            "smukke1234",
            66,
            R.drawable.avatar_amir
        ),
        User(
            "628e3c0ef24148cf460a1672",
            "cesar_rincon",
            "2020-01-11",
            "S??ren",
            "Mikkelsen",
            "Kbh",
            "sj@mail.dk",
            "smukke1234",
            643,
            R.drawable.avatar_cesar_rincon
        ),
        User(
            "628e3c0ef24148cf460a1673",
            "Clemme",
            "2012-02-15",
            "S??ren",
            "Clemmesen",
            "Odense",
            "sj@mail.dk",
            "smukke1234",
            864,
            R.drawable.avatar_clem_onojeghuo
        ),
        User(
            "628e3c0ef24148cf460a1674",
            "hBusing",
            "2022-05-25",
            "Hannah",
            "Busing",
            "Vejle",
            "sj@mail.dk",
            "smukke1234",
            42,
            R.drawable.avatar_hannah_busing
        ),
        User(
            "628e3c0ef24148cf460a1675aaa",
            "klp",
            "2021-04-13",
            "Kasper",
            "KP",
            "Kbh",
            "sj@mail.dk",
            "smukke1234",
            12,
            R.drawable.avatar_madison_lavern
        ),
        User(
            "628e3c0ef24148cf460a1676",
            "imansyah",
            "2021-04-13",
            "Imansyah",
            "KP",
            "Kbh",
            "sj@mail.dk",
            "smukke1234",
            85,
            R.drawable.avatar_imansyah
        ),
        User(
            "628e3c0ef24148cf460a1677",
            "Janko",
            "2021-04-13",
            "Kim",
            "Janko",
            "Kbh",
            "sj@mail.dk",
            "smukke1234",
            99,
            R.drawable.avatar_janko
        ),

        )


    var posts = mutableListOf<Post>(
        Post(
            "628f6bb51081edec4463f290",
            mutableListOf(
                Comment(
                    "628e3c0ef24148cf460a1671",
                    "Sp??rgeS??ren",
                    "Jeg kan godt lide yamaha trommer",
                    "628f6bb5hdhdhd1081edec4463f291a",
                    mutableListOf<Like>(
                        Like("628e3c0ef24148cf460a1677", "Janko")
                    ) as ArrayList<Like>,
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                ),
                Comment(
                    "628e3c0ef24148cf460a1674",
                    "hBusing",
                    "De er ogs?? fede ",
                    "628f6basasb51081edec4463f291",
                    mutableListOf<Like>(
                        Like("628e3c0ef24148cf460a1677", "Janko")
                    ) as ArrayList<Like>,
                    "2022-05-26",
                    "2022-05-26",
                    mutableListOf<Quote>(
                        Quote("628f6bb51081edec4463f291a", "Sp??rgeS??ren", "628e3c0ef24148cf460a1671", "Jeg kan godt lide yamaha trommer")
                    ) as ArrayList<Quote>
                ),
                Comment(
                    "628e3c0ef24148cf460a1671",
                    "Sp??rgeS??ren",
                    "ja, mit s??t er bl??t",
                    "628f6bb51081edec4463f29112",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    mutableListOf<Quote>(
                        Quote("628f6bb51081edec4463f291", "Sp??rgeS??ren", "628e3c0ef24148cf460a1671", "Jeg kan godt lide yamaha trommer"),
                        Quote("628f6bb51081edec4463f291", "hBusing", "628e3c0ef24148cf460a1674", "De er ogs?? fede ")
                    ) as ArrayList<Quote>
                )

            ) as ArrayList<Comment>
        ),
        Post(
            "628f6bb51081edec4463f290e",
            mutableListOf(
                Comment(
                    "628e3c0ef24148cf460a1674",
                    "hBusing",
                    "Se mit nye, fede s??t",
                    "628f6basaasfvb51081edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                ),
                Comment(
                    "628e3c0ef24148cf460a1677",
                    "Janko",
                    "Det er super flot!",
                    "628f6vdvfvabb51081edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                )

            ) as ArrayList<Comment>
        ),
        Post(
            "628f6bb51081edec4d463f290",
            mutableListOf(
                Comment(
                    "628e3c0ef24148cf460a1676",
                    "imansyah",
                    "Hej medslagere. Der ligger en fin dokumentar om Mavis Staples p?? dr.dk - ??n af mine store helte. Is??r er hendes nyere plader mig en stor inspiration ift produktion etc. Derudover er hele hendes historie s??rdeles sp??ndende. Hermed en stor anbefaling!",
                    "628f6bbiii51081edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                ),
                Comment(
                    "628e3c0ef24148cf460a1677",
                    "Janko",
                    "Se mit nye, fede s??t EDIT",
                    "628f6bb51080981edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                )
            ) as ArrayList<Comment>
        ),
        Post(
            "628f6bb5d1081edec4463f290",
            mutableListOf(
                Comment(
                    "628e3c0ef24148cf460a1672",
                    "cesar_rincon",
                    " Jeg kommer sgu' nok til at spille lidt igen, selv om jeg havde lagt det p?? hylden, men...\n" +
                            "\n" +
                            "Denne gang skal det v??re noget andet. Tanken er, at jeg starter mere eller mindre forfra og f??r styret de basis ting af, jeg har sprunget over s?? mange gange.\n" +
                            "\n" +
                            "Det jeg leder efter, er en gammeldags l??rer med fast undervisning, lektier, noder osv., men det skal selvf??lgelig v??re en der kan sine ting.\n" +
                            "\n" +
                            "Jeg har spillet on and off de sidste 30 ??r og leder ikke efter en halvstuderet r??ver, men en der ved hvad der skal prioriteres.\n" +
                            "\n" +
                            "Jeg har haft timer hos et par prof'er hist og pist, men oftest tager de den bare p?? rutinen og hyggesludrer for resten. For mig, giver det ikke meget mere, end at l??se et nummer af modern drummer. Det er bare en del dyrere.....\n" +
                            "\n" +
                            "Nogen bud p?? en god kandidat ? ",
                    "628f6bb510851515t1edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                ),
                Comment(
                    "628e3c0ef24148cf460a1677",
                    "Janko",
                    "Se mit nye, fede s??t EDIT",
                    "628f6bb510hej81edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                )
            ) as ArrayList<Comment>
        ),
        Post(
            "2628f6bb51081edec4463f290",
            mutableListOf(
                Comment(
                    "628e3c0ef24148cf460a1671",
                    "Sp??rgeS??ren",
                    "Bruger en lille 8-kanals Behringer mixer til in-ears - og blot hovedtelefon-udgangen derfra. M??ske man med fordel kan bruge en hovedtelefons-forforst??rker for bedre lyd i in-ears - har aldrig pr??vet det?\n" +
                            "\n" +
                            "Mine in-ears er Shure SE215:\n" +
                            "\n" +
                            "http://www.4sound.dk/pa/in-ear-systemer/monitors/shure-se215-in-ear-hovedtelefon-k-black\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "K??bte p?? et tidspunkt deres dyrere SE535 - men de var al for skinger i diskanten til mine ??rer, l??d ganske forf??rdeligt - og koster jo 4 gange, hvad du kan k??be SE215 for - s?? det g??r jeg ikke igen.\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "Har h??rt, nogle har problemer med sved, som ??del??gger ledninger og stik i Shure'ne - det har jeg aldrig oplevet. Har brugt samme in-ears i 5 ??r nu - m??ske omkring 100 jobs ...",
                    "628f6bb5108olle1edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                ),
                Comment(
                    "628e3c0ef24148cf460a1677",
                    "Janko",
                    "Se mit nye, fede s??t EDIT",
                    "628f6bb8751081edec4463f291",
                    ArrayList<Like>(),
                    "2022-05-26",
                    "2022-05-26",
                    ArrayList<Quote>()
                )
            ) as ArrayList<Comment>
        )
    )


}