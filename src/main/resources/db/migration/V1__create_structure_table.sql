CREATE TABLE "user" (
  "id" UUID,
  "role_id" UUID,
  "username" Varchar(60) NOT NULL,
  "email" Varchar(100) NOT NULL,
  "profession" Varchar(500) NOT NULL,
  "experience" TEXT,
  "skills" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "tag" (
  "id" UUID,
  "name" Varchar(60),
  PRIMARY KEY ("id")
);