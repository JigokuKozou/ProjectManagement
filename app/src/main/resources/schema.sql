CREATE TABLE employee (
    id BIGSERIAL,
    surname VARCHAR(30) NOT NULL,
    name VARCHAR(20) NOT NULL,
    patronymic VARCHAR(30) NULL,
    job_title VARCHAR(100) NULL,
    email VARCHAR(256) NULL,
    status SMALLINT NOT NULL,
    login VARCHAR(20) NULL UNIQUE,
    password VARCHAR(128) NULL,
    CONSTRAINT employee_pk PRIMARY KEY (id)
);

CREATE TABLE team_member (
    id BIGSERIAL,
    employee_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    role SMALLINT NOT NULL,
    CONSTRAINT team_member_pk PRIMARY KEY (id)
);

CREATE TABLE project_team (
    id BIGSERIAL,
    project_id BIGINT NOT NULL,
    CONSTRAINT project_team_pk PRIMARY KEY (id)
);

CREATE TABLE project (
    id BIGSERIAL,
    code_name VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT NULL,
    status SMALLINT NOT NULL,
    CONSTRAINT project_pk PRIMARY KEY (id)
);

CREATE TABLE task (
    id SERIAL,
    name VARCHAR(100) NOT NULL,
    description TEXT NULL,
    executor_id BIGINT NULL,
    estimate_hours INT CHECK (estimate_hours >= 0) NOT NULL,
    deadline TIMESTAMP WITH TIME ZONE NOT NULL,
    status SMALLINT NOT NULL,
    author_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT task_pk PRIMARY KEY (id)
);

ALTER TABLE team_member ADD CONSTRAINT team_member_employee_fk FOREIGN KEY (employee_id) REFERENCES employee(id);
ALTER TABLE team_member ADD CONSTRAINT team_member_team_fk FOREIGN KEY (team_id) REFERENCES project_team(id);

ALTER TABLE project_team ADD CONSTRAINT project_team_project_fk FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE task ADD CONSTRAINT task_executor_fk FOREIGN KEY (executor_id) REFERENCES team_member(id);
ALTER TABLE task ADD CONSTRAINT task_author_fk FOREIGN KEY (author_id) REFERENCES team_member(id);
