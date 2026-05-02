/*
❌ 外部 Comparator class



*/

package NestedClass.domain;

import java.util.Comparator;

public class EmployeeComparator <T extends Employee> implements Comparator<Employee> {

    @Override
    // 缺點 ❌ 不能直接 access private field , 需要 getter
    public int compare(Employee o1, Employee o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
