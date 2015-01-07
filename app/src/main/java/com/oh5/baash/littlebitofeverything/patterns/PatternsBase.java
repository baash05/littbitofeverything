package com.oh5.baash.littlebitofeverything.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 7/01/15.
 */
public abstract class PatternsBase {
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
}
