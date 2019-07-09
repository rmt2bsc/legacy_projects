package com.services;

import java.util.LinkedList;

class Solution {
    public int solution(int[] A) {
        int nextNode = 0;
        int listSize = 0;
        while(true) {
            nextNode = A[nextNode];
            listSize++;
            if (nextNode == -1) {
                break;
            }
        }
        return listSize;
    }
   
    public int largestNumber(int data) {
        int num = data;
        int[] times = new int[10];
        while (num != 0) {
            if (num == 0) {
                break;
            }
            int val = num % 10;
            times[val]++;
            num /= 10;
        }
        String largestNumber = "";
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < times[i]; j++) {
                largestNumber += i;
                System.out.println("Current no.: " + largestNumber);
            }
        }
        return Integer.parseInt(largestNumber);
    }
    
    
    public  int getStackSolution(String s) {
        LinkedList<Integer> stack = new LinkedList(); // 'Stack' would be a good class as well

        if (s == null || s.length() == 0)
                return -1;

        String instructions[] = s.split(" ");
                
        for (int n=0; n< instructions.length; ++n) {
                String op = instructions[n];

                if (isNumeric(op)) { // Check whether the char represents a digit
                        stack.push(Integer.parseInt(op));
                } else if (stack.size() >= 1) { // There must be at least two entries on the stack in order to start operating
                        if (op.equals( "+")) {
                                int x = stack.pop() + stack.pop();
                                stack.push(x);
                        } else if (op.equals( "-")) {
                                int x = stack.pop() - stack.pop();
                                if (x >= 0) {
                                        stack.push(x);
                                }
                                else {
                                    return -1;
                                }
                        } else if(op.equals("DUP")) {
                            int x = stack.pop();
                            stack.push(x);
                            stack.push(x);
                        } else if (op.equals("POP")) {
                            stack.pop();
                        }
                } else return -1;
        }

        return stack.pop();
}
    
    private  boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        int results = 0;
        
        int intList[] = {1, 4, -1, 3, 2};
        Solution s = new Solution();
        
        String str = "13 DUP 4 POP 5 DUP + DUP + -";
//        String a[] = str.split(" ");
        results = s.getStackSolution(str);
        System.out.println("Stack Results: " + results);
        str = "13 4 -";
        results = s.getStackSolution(str);
        System.out.println("Stack Results: " + results);
        
        results = s.solution(intList);
        System.out.print("Size of link list: " + results);
        
        results = s.largestNumber(553);
        System.out.println("The largest number is:  " + results);
    }
    
    
    
}