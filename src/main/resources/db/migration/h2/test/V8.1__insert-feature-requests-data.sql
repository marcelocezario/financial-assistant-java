INSERT INTO public.feature_requests (description,rating,developed,approved,active,created_at,updated_at) VALUES
	 ('Habilitar envio de e-mail',0,false,true,true,'2024-06-19 07:59:58.428185-03','2024-06-19 09:18:50.900963-03'),
	 ('Campo de seleção de cores no IOS (iphone)',0,false,true,true,'2024-06-19 08:01:25.093568-03','2024-06-19 08:08:16.653764-03'),
	 ('Campo de data e hora em dispositivos móveis',0,false,true,true,'2024-06-19 08:02:05.625892-03','2024-06-19 08:02:05.6259-03'),
	 ('Indicador de carregamento (loader)',5,false,true,true,'2024-06-19 08:02:55.957157-03','2024-06-19 08:02:55.957167-03'),
	 ('Ajustar componente de tempo relativo',4,false,true,true,'2024-06-19 08:03:48.528172-03','2024-06-19 08:03:48.528181-03'),
	 ('Habilitar opção de exibir senha no cadastro de usuário',0,false,true,true,'2024-06-19 08:04:42.456903-03','2024-06-19 08:04:42.456916-03'),
	 ('Alterar nomes de tipo de transação',0,false,true,true,'2024-06-19 08:05:40.310625-03','2024-06-19 08:05:40.310634-03'),
	 ('Preencher campo confirmar senha ao usar sugestão do navegador',0,false,true,true,'2024-06-19 08:06:19.777343-03','2024-06-19 08:06:19.777353-03');

INSERT INTO public.feature_requests_translation (feature_request_id,language_code,title,description,active,created_at,updated_at) VALUES
	 (1,'en-us','Enable email sending','Enable email sending for user creation and password recovery',true,'2024-06-19 07:59:58.432142-03','2024-06-19 07:59:58.432157-03'),
	 (1,'pt-br','Habilitar envio de e-mail','Habilitar envio de e-mail para criação de usuário e recuperação de senha',true,'2024-06-19 07:59:58.435527-03','2024-06-19 07:59:58.43554-03'),
	 (2,'en-us','Color selection field on IOS (iPhone)','On iPhone, the color selection field did not open the dialog for selection',true,'2024-06-19 08:01:25.095307-03','2024-06-19 08:01:25.095315-03'),
	 (2,'pt-br','Campo de seleção de cores no IOS (iphone)','No iphone, o campo de seleção de cores não abriu o dialog para seleção',true,'2024-06-19 08:01:25.097042-03','2024-06-19 08:01:25.097051-03'),
	 (3,'en-us','Date and time field on mobile devices','The device itself provides a selection dialog, so having 2 options like on iPhone causes a screen bug when opening one and closing the other',true,'2024-06-19 08:02:05.62805-03','2024-06-19 08:02:05.62806-03'),
	 (3,'pt-br','Campo de data e hora em dispositivos móveis','O próprio dispositivo fornece um dialog de seleção, ficando estranho 2 opções como no iphone que da um bug na tela ao abrir um e fechar o outro',true,'2024-06-19 08:02:05.630127-03','2024-06-19 08:02:05.630136-03'),
	 (4,'en-us','Loading indicator (loader)','Without the loader, the user doesn''t know if the request was made, if there is any processing, or if the application has frozen',true,'2024-06-19 08:02:55.961658-03','2024-06-19 08:02:55.961668-03'),
	 (4,'pt-br','Indicador de carregamento (loader)','Sem o carregamento, o usuário não sabe se a requisição foi realizada, se está tendo algum processamento, ou se a aplicação travou',true,'2024-06-19 08:02:55.967248-03','2024-06-19 08:02:55.96726-03'),
	 (5,'en-us','Adjust relative time component','Relative time component (e.g., ''1 week ago''), needs to be internationalized and adjusted for display times in transactions',true,'2024-06-19 08:03:48.529751-03','2024-06-19 08:03:48.52976-03'),
	 (5,'pt-br','Ajustar componente de tempo relativo','Componente de tempo relativo (ex. ''1 semana atrás''), precisa ser internacionalizado e ajustado os tempos de exibição em transações',true,'2024-06-19 08:03:48.531653-03','2024-06-19 08:03:48.531662-03'),
	 (6,'en-us','Enable option to show password in user registration','When registering a user, it''s necessary to enter a password. For a better user experience, add a button to show the password',true,'2024-06-19 08:04:42.458487-03','2024-06-19 08:04:42.458499-03'),
	 (6,'pt-br','Habilitar opção de exibir senha no cadastro de usuário','Ao realizar cadastro do usuário, é necessário inserir uma senha, para melhor experiencia do usuário, adicionar um botão com opção de exibir a senha',true,'2024-06-19 08:04:42.46042-03','2024-06-19 08:04:42.460429-03'),
	 (7,'en-us','Change transaction type names','''Debit'' and ''credit'' names can be confusing for some users in transaction types. Change to ''payment'' and ''receipt''',true,'2024-06-19 08:05:40.312759-03','2024-06-19 08:05:40.312768-03'),
	 (7,'pt-br','Alterar nomes de tipo de transação','Nomes ''débito'' e ''crédito'' podem ser confusos para alguns usuários no tipo de transação, alterar para ''pagamento'' e ''recebimento''',true,'2024-06-19 08:05:40.314833-03','2024-06-19 08:05:40.314841-03'),
	 (8,'en-us','Fill confirm password field when using browser suggestion','When using a password suggested by the browser, automatically fill the confirm password field',true,'2024-06-19 08:06:19.779419-03','2024-06-19 08:06:19.779428-03'),
	 (8,'pt-br','Preencher campo confirmar senha ao usar sugestão do navegador','Ao usar senha sugerida pelo navegador, preencher automaticamente campo confirmar senha',true,'2024-06-19 08:06:19.781472-03','2024-06-19 08:06:19.781484-03');
