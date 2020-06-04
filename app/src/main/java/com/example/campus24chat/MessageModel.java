package com.example.campus24chat;
public class MessageModel {
    private String message_content;
    private String message_image_url;
    private String sent_date_time;
    private String senders_username;
    private String senders_profile_pic;
    private String senders_unique_id;
    private String seen_by;

    private String date_changed;
    private String group_joined_by;
    private String group_left_by;
    private String new_group_admin;

    private String admin_name_who_removed_the_participant;
    private String admin_removed_participant_with_name;


    private String type;

    public MessageModel() {
        message_content = "This message is being been fetched!!!";
        message_image_url = "0";
        sent_date_time = "0";
        senders_username = "Admin";
        senders_profile_pic = "https://eitrawmaterials.eu/wp-content/uploads/2016/09/person-icon.png";
        senders_unique_id = "0";
        seen_by = "0";

        date_changed = "0";
        group_joined_by = "0";
        group_left_by = "0";
        new_group_admin = "0";

        admin_name_who_removed_the_participant = "0";
        admin_removed_participant_with_name = "0";
        type = "individual";

    }

    public MessageModel(String message_content, String message_image_url, String sent_date_time, String senders_username, String senders_profile_pic, String senders_unique_id, String seen_by, String date_changed, String group_joined_by, String group_left_by, String new_group_admin, String admin_name_who_removed_the_participant, String admin_removed_participant_with_name, String type) {
        this.message_content = message_content;
        this.message_image_url = message_image_url;
        this.sent_date_time = sent_date_time;
        this.senders_username = senders_username;
        this.senders_profile_pic = senders_profile_pic;
        this.senders_unique_id = senders_unique_id;
        this.seen_by = seen_by;
        this.date_changed = date_changed;
        this.group_joined_by = group_joined_by;
        this.group_left_by = group_left_by;
        this.new_group_admin = new_group_admin;
        this.admin_name_who_removed_the_participant = admin_name_who_removed_the_participant;
        this.admin_removed_participant_with_name = admin_removed_participant_with_name;
        this.type = type;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_image_url() {
        return message_image_url;
    }

    public void setMessage_image_url(String message_image_url) {
        this.message_image_url = message_image_url;
    }

    public String getSent_date_time() {
        return sent_date_time;
    }

    public void setSent_date_time(String sent_date_time) {
        this.sent_date_time = sent_date_time;
    }

    public String getSenders_username() {
        return senders_username;
    }

    public void setSenders_username(String senders_username) {
        this.senders_username = senders_username;
    }

    public String getSenders_profile_pic() {
        return senders_profile_pic;
    }

    public void setSenders_profile_pic(String senders_profile_pic) {
        this.senders_profile_pic = senders_profile_pic;
    }

    public String getSenders_unique_id() {
        return senders_unique_id;
    }

    public void setSenders_unique_id(String senders_unique_id) {
        this.senders_unique_id = senders_unique_id;
    }

    public String getSeen_by() {
        return seen_by;
    }

    public void setSeen_by(String seen_by) {
        this.seen_by = seen_by;
    }

    public String getDate_changed() {
        return date_changed;
    }

    public void setDate_changed(String date_changed) {
        this.date_changed = date_changed;
    }

    public String getGroup_joined_by() {
        return group_joined_by;
    }

    public void setGroup_joined_by(String group_joined_by) {
        this.group_joined_by = group_joined_by;
    }

    public String getGroup_left_by() {
        return group_left_by;
    }

    public void setGroup_left_by(String group_left_by) {
        this.group_left_by = group_left_by;
    }

    public String getNew_group_admin() {
        return new_group_admin;
    }

    public void setNew_group_admin(String new_group_admin) {
        this.new_group_admin = new_group_admin;
    }

    public String getAdmin_name_who_removed_the_participant() {
        return admin_name_who_removed_the_participant;
    }

    public void setAdmin_name_who_removed_the_participant(String admin_name_who_removed_the_participant) {
        this.admin_name_who_removed_the_participant = admin_name_who_removed_the_participant;
    }

    public String getAdmin_removed_participant_with_name() {
        return admin_removed_participant_with_name;
    }

    public void setAdmin_removed_participant_with_name(String admin_removed_participant_with_name) {
        this.admin_removed_participant_with_name = admin_removed_participant_with_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}