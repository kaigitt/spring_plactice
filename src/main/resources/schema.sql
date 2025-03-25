-- プロジェクトテーブル
CREATE TABLE IF NOT EXISTS projects (
    project_id VARCHAR(10) PRIMARY KEY,
    project_name VARCHAR(100),
    sales_user_id varchar(6),
    sales_user_name varchar(20),
    project_manager_id varchar(6),
    project_manager_name varchar(20),
    contract_date DATE
);

-- WBSテーブル
CREATE TABLE wbs (
    wbs_no VARCHAR(50) PRIMARY KEY,
    project_id VARCHAR(50) NOT NULL,
    wbs_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);

-- 技術者テーブル
CREATE TABLE engineers (
    engineer_id VARCHAR(50) PRIMARY KEY,
    wbs_no VARCHAR(50) NOT NULL,
    engineer_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (wbs_no) REFERENCES wbs(wbs_no)
);

-- WBSと技術者の関連テーブル
CREATE TABLE IF NOT EXISTS wbs_engineers (
    wbs_no VARCHAR(10),
    engineer_id VARCHAR(10),
    PRIMARY KEY (wbs_no, engineer_id),
    FOREIGN KEY (wbs_no) REFERENCES wbs(wbs_no),
    FOREIGN KEY (engineer_id) REFERENCES engineers(engineer_id)
); 