package extendfunction.pojo;

public class Dept {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mybatis_dept.did
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    private Integer did;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mybatis_dept.dept_name
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    private String deptName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mybatis_dept.did
     *
     * @return the value of mybatis_dept.did
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    public Integer getDid() {
        return did;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mybatis_dept.did
     *
     * @param did the value for mybatis_dept.did
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mybatis_dept.dept_name
     *
     * @return the value of mybatis_dept.dept_name
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mybatis_dept.dept_name
     *
     * @param deptName the value for mybatis_dept.dept_name
     *
     * @mbggenerated Thu Mar 16 10:43:45 CST 2023
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
}