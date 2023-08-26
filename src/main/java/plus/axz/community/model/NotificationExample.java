package plus.axz.community.model;

import java.util.ArrayList;
import java.util.List;

public class NotificationExample {
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public NotificationExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNotifierIdIsNull() {
            addCriterion("notifier_id is null");
            return (Criteria) this;
        }

        public Criteria andNotifierIdIsNotNull() {
            addCriterion("notifier_id is not null");
            return (Criteria) this;
        }

        public Criteria andNotifierIdEqualTo(Long value) {
            addCriterion("notifier_id =", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdNotEqualTo(Long value) {
            addCriterion("notifier_id <>", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdGreaterThan(Long value) {
            addCriterion("notifier_id >", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("notifier_id >=", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdLessThan(Long value) {
            addCriterion("notifier_id <", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdLessThanOrEqualTo(Long value) {
            addCriterion("notifier_id <=", value, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdIn(List<Long> values) {
            addCriterion("notifier_id in", values, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdNotIn(List<Long> values) {
            addCriterion("notifier_id not in", values, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdBetween(Long value1, Long value2) {
            addCriterion("notifier_id between", value1, value2, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifierIdNotBetween(Long value1, Long value2) {
            addCriterion("notifier_id not between", value1, value2, "notifierId");
            return (Criteria) this;
        }

        public Criteria andNotifyNameIsNull() {
            addCriterion("notify_name is null");
            return (Criteria) this;
        }

        public Criteria andNotifyNameIsNotNull() {
            addCriterion("notify_name is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyNameEqualTo(String value) {
            addCriterion("notify_name =", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotEqualTo(String value) {
            addCriterion("notify_name <>", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameGreaterThan(String value) {
            addCriterion("notify_name >", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameGreaterThanOrEqualTo(String value) {
            addCriterion("notify_name >=", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLessThan(String value) {
            addCriterion("notify_name <", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLessThanOrEqualTo(String value) {
            addCriterion("notify_name <=", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLike(String value) {
            addCriterion("notify_name like", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotLike(String value) {
            addCriterion("notify_name not like", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameIn(List<String> values) {
            addCriterion("notify_name in", values, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotIn(List<String> values) {
            addCriterion("notify_name not in", values, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameBetween(String value1, String value2) {
            addCriterion("notify_name between", value1, value2, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotBetween(String value1, String value2) {
            addCriterion("notify_name not between", value1, value2, "notifyName");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNull() {
            addCriterion("receiver_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNotNull() {
            addCriterion("receiver_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdEqualTo(Long value) {
            addCriterion("receiver_id =", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotEqualTo(Long value) {
            addCriterion("receiver_id <>", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThan(Long value) {
            addCriterion("receiver_id >", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThanOrEqualTo(Long value) {
            addCriterion("receiver_id >=", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThan(Long value) {
            addCriterion("receiver_id <", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThanOrEqualTo(Long value) {
            addCriterion("receiver_id <=", value, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIn(List<Long> values) {
            addCriterion("receiver_id in", values, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotIn(List<Long> values) {
            addCriterion("receiver_id not in", values, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdBetween(Long value1, Long value2) {
            addCriterion("receiver_id between", value1, value2, "receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotBetween(Long value1, Long value2) {
            addCriterion("receiver_id not between", value1, value2, "receiverId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNull() {
            addCriterion("target_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNotNull() {
            addCriterion("target_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetIdEqualTo(Long value) {
            addCriterion("target_id =", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotEqualTo(Long value) {
            addCriterion("target_id <>", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThan(Long value) {
            addCriterion("target_id >", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("target_id >=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThan(Long value) {
            addCriterion("target_id <", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThanOrEqualTo(Long value) {
            addCriterion("target_id <=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIn(List<Long> values) {
            addCriterion("target_id in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotIn(List<Long> values) {
            addCriterion("target_id not in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdBetween(Long value1, Long value2) {
            addCriterion("target_id between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotBetween(Long value1, Long value2) {
            addCriterion("target_id not between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetTitleIsNull() {
            addCriterion("target_title is null");
            return (Criteria) this;
        }

        public Criteria andTargetTitleIsNotNull() {
            addCriterion("target_title is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTitleEqualTo(String value) {
            addCriterion("target_title =", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleNotEqualTo(String value) {
            addCriterion("target_title <>", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleGreaterThan(String value) {
            addCriterion("target_title >", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleGreaterThanOrEqualTo(String value) {
            addCriterion("target_title >=", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleLessThan(String value) {
            addCriterion("target_title <", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleLessThanOrEqualTo(String value) {
            addCriterion("target_title <=", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleLike(String value) {
            addCriterion("target_title like", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleNotLike(String value) {
            addCriterion("target_title not like", value, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleIn(List<String> values) {
            addCriterion("target_title in", values, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleNotIn(List<String> values) {
            addCriterion("target_title not in", values, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleBetween(String value1, String value2) {
            addCriterion("target_title between", value1, value2, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTargetTitleNotBetween(String value1, String value2) {
            addCriterion("target_title not between", value1, value2, "targetTitle");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Long value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Long value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Long value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Long value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Long value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Long value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Long> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Long> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Long value1, Long value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Long value1, Long value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}