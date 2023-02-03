ALTER TABLE events ADD template_id BIGINT NULL;

ALTER TABLE events ADD CONSTRAINT fk_events_template_id FOREIGN KEY ( template_id ) REFERENCES templates ( id );

ALTER TABLE templates ADD user_id BIGINT NULL;

ALTER TABLE templates ADD CONSTRAINT fk_templates_user_id FOREIGN KEY ( user_id ) REFERENCES users ( id );