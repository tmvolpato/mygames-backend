INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope,
                                 authorized_grant_types, web_server_redirect_uri, authorities,
                                 access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES
  ('angular', 'MY_GAMES_ID', '12345', 'read,write', 'password,refresh_token', null, null, 36000, 36000, null, true);
