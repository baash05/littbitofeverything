package com.oh5.baash.littlebitofeverything.patterns;

/**
 * Created by davidrawk on 3/01/15.
 */
public class NumberPattern extends BasePattern {
        private int m_size = 10;

        public NumberPattern(int size){
            m_right_answer = m_randomiser.nextInt(20);
            m_size = size;
        }

        public String [] get_answers(){
            if( m_answers == null) {
                m_answers = new String[3];
                int [] answer_ints = get_answer_ints();
                for(int x = 0; x < 3; x++){
                    m_answers[x] = "" + answer_ints[x];
                }
            }
            return m_answers;
        }

        public String[] get_puzzle(){
            if( m_pattern == null) {
                m_pattern = new String[m_size];
                int lowest = m_right_answer - (m_randomiser.nextInt(m_size / 2));
                if(lowest < 0)               lowest = 0;
                if((lowest + m_size) > 20 ) lowest = 20-m_size;

                for (int x = 0; x < m_size; x++) {
                    m_pattern[x] = "" + (lowest + x);
                }
                if(m_randomiser.nextBoolean()) {
                    revers_pattern();
                }
            }
            return m_pattern;
        }

        private void revers_pattern(){
            for (int i = 0; i < m_size / 2; i++) {
                String temp = m_pattern[i];
                m_pattern[i] = m_pattern[m_size - 1 - i];
                m_pattern[m_size - 1 - i] = temp;
            }
        }
        public int get_size(){
            return m_size;
        }
}
