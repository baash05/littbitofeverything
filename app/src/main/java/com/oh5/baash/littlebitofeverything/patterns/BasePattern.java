package com.oh5.baash.littlebitofeverything.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 7/01/15.
 */
public abstract class BasePattern {
    protected int m_right_answer;
    protected String [] m_answers = null;
    protected String [] m_pattern = null;
    protected Random m_randomiser = new Random();

    public String get_right_answer(){
        return "" + m_right_answer;
    }
    abstract public String[] get_puzzle();
    abstract public String [] get_answers();
    abstract public int get_size();

    protected int[] get_answer_ints(){
        int []answers = new int[3];
        int random_number;
        for(int x = 0; x < 3; x++){
            random_number = m_randomiser.nextInt(7)+1;
            if( m_randomiser.nextBoolean() ) {
                random_number *= -1;
            }
            answers[x] = (m_right_answer + random_number + 26)%26;
        }
        int right_index = m_randomiser.nextInt(3);
        answers[right_index] = m_right_answer;
        return answers;
    }
}
