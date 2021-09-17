package com.altaie.triviagame.ui.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.data.Quiz
import com.altaie.triviagame.databinding.ChallengeCardItemBinding

class ChallengeAdapter(private val list: List<Quiz>) :
    RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ChallengeCardItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = R.layout.challenge_card_item
        val itemView = LayoutInflater.from(parent.context).inflate(item, parent, false)
        return ViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            DataManager.quizzes[position].apply {
                val list = mutableListOf<String>()
                list.addAll(incorrectAnswers)
                list.add(correctAnswer)
                list.shuffle()

                group.children.forEach { view ->
                    view.setOnClickListener {
                        group.children.forEach{
                            it.isEnabled = false
                        }
                    }
                }


                holder.binding.question.text = question
                optionOne.apply {
                    text = list[0]
                    setOnCheckedChangeListener { _, _ ->
                        val message: String = if (text == correctAnswer) {
                            "Correct"
                        } else {
                            "Incorrect"
                        }

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                optionTwo.apply {
                    text = list[1]
                    setOnCheckedChangeListener { _, _ ->
                        val message: String = if (text == correctAnswer) {
                            "Correct"
                        } else {
                            "Incorrect"
                        }

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                optionThree.apply {
                    text = list[2]
                    setOnCheckedChangeListener { _, _ ->
                        val message: String = if (text == correctAnswer) {
                            "Correct"
                        } else {
                            "Incorrect"
                        }

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                optionFour.apply {
                    text = list[3]
                    setOnCheckedChangeListener { _, _ ->
                        val message: String = if (text == correctAnswer) {
                            "Correct"
                        } else {
                            "Incorrect"
                        }

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount() = list.size

}