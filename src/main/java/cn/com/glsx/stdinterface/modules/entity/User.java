package cn.com.glsx.stdinterface.modules.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_user")
public class User implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Long gender;

    /**
     * 岗位
     */
    private String position;

    /**
     * 租户id
     */
    @Column(name = "tenant_id")
    private Integer tenantId;

    /**
     * 部门id
     */
    @Column(name = "department_id")
    private Integer departmentId;

    /**
     * 上级id
     */
    @Column(name = "superior_id")
    private Integer superiorId;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 是否超级管理员：true是，false否
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 全局用户类型(跨租户)：0普通用户，1管理员
     */
    @Column(name = "global_type")
    private Byte globalType;

    /**
     * 局部账号类型(限定租户)：有效
     */
    @Column(name = "local_type")
    private Byte localType;

    /**
     * 状态（1=启用 2=禁用）
     */
    @Column(name = "enable_status")
    private Byte enableStatus;

    /**
     * 是否删除 0=正常 -1=删除
     */
    @Column(name = "del_flag")
    private Byte delFlag;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 创建人用户id
     */
    @Column(name = "created_by")
    private Integer createdBy;

    /**
     * 更新时间
     */
    @Column(name = "updated_date")
    private Date updatedDate;

    /**
     * 更新人用户id
     */
    @Column(name = "updated_by")
    private Integer updatedBy;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取账户名
     *
     * @return account - 账户名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账户名
     *
     * @param account 账户名
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone_number - 手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号
     *
     * @param phoneNumber 手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * 获取头像
     *
     * @return portrait - 头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置头像
     *
     * @param portrait 头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public Long getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Long gender) {
        this.gender = gender;
    }

    /**
     * 获取岗位
     *
     * @return position - 岗位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置岗位
     *
     * @param position 岗位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取租户id
     *
     * @return tenant_id - 租户id
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * 设置租户id
     *
     * @param tenantId 租户id
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取部门id
     *
     * @return department_id - 部门id
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置部门id
     *
     * @param departmentId 部门id
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取上级id
     *
     * @return superior_id - 上级id
     */
    public Integer getSuperiorId() {
        return superiorId;
    }

    /**
     * 设置上级id
     *
     * @param superiorId 上级id
     */
    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    /**
     * 获取加密盐
     *
     * @return salt - 加密盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置加密盐
     *
     * @param salt 加密盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取是否超级管理员：true是，false否
     *
     * @return is_admin - 是否超级管理员：true是，false否
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否超级管理员：true是，false否
     *
     * @param isAdmin 是否超级管理员：true是，false否
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取全局用户类型(跨租户)：0普通用户，1管理员
     *
     * @return global_type - 全局用户类型(跨租户)：0普通用户，1管理员
     */
    public Byte getGlobalType() {
        return globalType;
    }

    /**
     * 设置全局用户类型(跨租户)：0普通用户，1管理员
     *
     * @param globalType 全局用户类型(跨租户)：0普通用户，1管理员
     */
    public void setGlobalType(Byte globalType) {
        this.globalType = globalType;
    }

    /**
     * 获取局部账号类型(限定租户)：有效
     *
     * @return local_type - 局部账号类型(限定租户)：有效
     */
    public Byte getLocalType() {
        return localType;
    }

    /**
     * 设置局部账号类型(限定租户)：有效
     *
     * @param localType 局部账号类型(限定租户)：有效
     */
    public void setLocalType(Byte localType) {
        this.localType = localType;
    }

    /**
     * 获取状态（1=启用 2=禁用）
     *
     * @return enable_status - 状态（1=启用 2=禁用）
     */
    public Byte getEnableStatus() {
        return enableStatus;
    }

    /**
     * 设置状态（1=启用 2=禁用）
     *
     * @param enableStatus 状态（1=启用 2=禁用）
     */
    public void setEnableStatus(Byte enableStatus) {
        this.enableStatus = enableStatus;
    }

    /**
     * 获取是否删除 0=正常 -1=删除
     *
     * @return del_flag - 是否删除 0=正常 -1=删除
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除 0=正常 -1=删除
     *
     * @param delFlag 是否删除 0=正常 -1=删除
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建时间
     *
     * @return created_date - 创建时间
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 设置创建时间
     *
     * @param createdDate 创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取创建人用户id
     *
     * @return created_by - 创建人用户id
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人用户id
     *
     * @param createdBy 创建人用户id
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取更新时间
     *
     * @return updated_date - 更新时间
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * 设置更新时间
     *
     * @param updatedDate 更新时间
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 获取更新人用户id
     *
     * @return updated_by - 更新人用户id
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置更新人用户id
     *
     * @param updatedBy 更新人用户id
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", portrait=").append(portrait);
        sb.append(", email=").append(email);
        sb.append(", gender=").append(gender);
        sb.append(", position=").append(position);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", superiorId=").append(superiorId);
        sb.append(", salt=").append(salt);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", globalType=").append(globalType);
        sb.append(", localType=").append(localType);
        sb.append(", enableStatus=").append(enableStatus);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}