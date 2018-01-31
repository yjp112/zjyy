
package com.supconit.base.entities;

import java.util.List;

import com.supconit.common.utils.StringUtil;
import com.supconit.common.web.entities.AuditExtend;

public class DutyGroupPerson extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long personId;
        private String personName;
        private Long groupId;
        private String postDesc;
        private String postName;
        private Long postId;
        private String post;//职位
        
        private String groupName;
        private String searchText;
        
        private String tel;
        private String code;
        
        private List<Long> searchDutyGroupList;
    
        public Long getPersonId(){
            return personId;
        }
        public void setPersonId(Long personId) {
		this.personId = personId;
	    }        
        public String getPersonName(){
            return personName;
        }
        public void setPersonName(String personName) {
		this.personName = personName;
	    }        
        public Long getGroupId(){
            return groupId;
        }
        public void setGroupId(Long groupId) {
		this.groupId = groupId;
	    }        
        public String getPostDesc(){
            return postDesc;
        }
        public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	    }
		 public String getGroupName() {
			return groupName;
		}
		 public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		 public List<Long> getSearchDutyGroupList() {
			return searchDutyGroupList;
		}
		 public void setSearchDutyGroupList(List<Long> searchDutyGroupList) {
			this.searchDutyGroupList = searchDutyGroupList;
		}
        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }


        public String getSearchText() {
            return searchText;
        }

        public void setSearchText(String searchText) {
            this.searchText = searchText;
        }

        public String getPost() {
            if(StringUtil.isNullOrEmpty(this.post))
                return "暂无";
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
}

