import weibo
import sys
import webbrowser
import time

APP_KEY = 242877311
APP_SECRET = 'bdffd1eada6e7f8ede6abe914f44a4cb'
REDIRECT_URL = 'http://127.0.0.1:8000/weibo'

if __name__ == '__main__':
	api = weibo.APIClient(APP_KEY, APP_SECRET)
	authorize_url = api.get_authorize_url(REDIRECT_URL)
	print(authorize_url)
	webbrowser.open_new(authorize_url)
	code = raw_input('authencation code: ')

	request = api.request_access_token(code, REDIRECT_URL)
	access_token = request.access_token
	expires_in = request.expires_in
	print 'access token: ', access_token
	print 'expire: ', expires_in

	api = weibo.APIClient(APP_KEY, APP_SECRET, redirect_uri=REDIRECT_URL)
	api.set_access_token(access_token, expires_in)
	# r = api.statuses.home_timeline.get(uid=2105112542, count=3)
	# for st in r.statuses
	# 	print st.text
	# print r.statuses.reposts_count
	# r = api.statuses.home_timeline.get(uid=1720788860,since_id=3880591176552354)
	# for st in r.statuses:
	# 	print st.user.name
	# 	print st.id
	# 	print st.text
	maxId = 3881300203223959
	r = api.comments.show.get(id=3881249556497850,count=30,page=1)
	for st in r.comments:
		print st.text
		print st.user.name
		print ''
	print 'main hello'