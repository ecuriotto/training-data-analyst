const languageAPI = require('./languageapi');
const feedbackStorage = require('./spanner');

exports.subscribe = function subscribe(event) {
  const pubsubMessage = Buffer.from(event.data, 'base64').toString();
  let feedbackObject = JSON.parse(pubsubMessage);
  console.log('Feedback object data before Language API:' + JSON.stringify(feedbackObject));
  // Run Natural Language API sentiment analysis
  return languageAPI.analyze(feedbackObject.feedback).then(score => {
  	console.log(`Score: ${score}`);
  	feedbackObject.score = score;
  	return feedbackObject;
  })
  .then(feedbackStorage.saveFeedback).then(() => {
  	console.log('feedback saved...');
  	return 'success';
  })
  .catch(console.error);
};