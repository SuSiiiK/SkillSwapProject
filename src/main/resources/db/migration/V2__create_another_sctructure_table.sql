ALTER TABLE "user" DROP COLUMN role_id;


CREATE TABLE "post" (
  "id" UUID,
  "user_id" UUID NOT NULL,
  "tags_id" UUID NOT NULL,
  "description" Varchar(1000) NOT NULL,
  "image" BYTEA,
  "created_at" Timestamp,
  PRIMARY KEY ("id")
);


CREATE TABLE "chat" (
  "id" UUID,
  "created_at" TIMESTAMP,
  PRIMARY KEY ("id")
);


CREATE TABLE "chat_members" (
  "id" UUID,
  "chat_id" UUID,
  "user_id" UUID,
  PRIMARY KEY ("id")
);

CREATE TABLE "post_tag" (
  "id" UUID,
  "post_id" UUID,
  "tag_id" UUID,
  PRIMARY KEY ("id")
);

CREATE TABLE "message" (
  "id" UUID,
  "message_text" TEXT NOT NULL,
  "image" BYTEA,
  "chat_id" UUID NOT NULL,
  "user_id" UUID NOT NULL,
  "created_at" TIMESTAMP,
  PRIMARY KEY ("id")
);