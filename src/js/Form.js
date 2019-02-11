import React, { Component } from 'react';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import MenuItem from '@material-ui/core/MenuItem';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import { ValidatorForm, TextValidator} from 'react-material-ui-form-validator';
import '../css/Form.css';

const styles = theme => ({
  button: {
    fontSize: '14pt',
    marginTop: '20px',
    textTransform: 'none',
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
});

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {
        courses: 'CIS-197',
        courseHistory: 'CIS-110 CIS-120 CIS-160',
        interests: 'Machine learning, artificial intelligence',
        difficulty: '3',
        mounted: false,
        courseQuality: '3',
        profQuality: '3',
        recs: [],
        noResults: false
    };

    this.handleCourseChange = this.handleCourseChange.bind(this);
    this.handleCourseHistoryChange = this.handleCourseHistoryChange.bind(this);
    this.handleInterestsChange = this.handleInterestsChange.bind(this);
    this.handleDifficultyChange = this.handleDifficultyChange.bind(this);
    this.handleCourseQualityChange = this.handleCourseQualityChange.bind(this);
    this.handleProfQualityChange = this.handleProfQualityChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.renderRecs = this.renderRecs.bind(this);
  }

  componentDidMount() {
    this.setState({ mounted: true });
  }

  handleCourseChange(event) {
    this.setState({courses: event.target.value});
  }

  handleCourseHistoryChange(event) {
    this.setState({courseHistory: event.target.value});
  }

  handleInterestsChange(event) {
    this.setState({interests: event.target.value});
  }

  handleDifficultyChange(event) {
    this.setState({difficulty: event.target.value});
  }

  handleCourseQualityChange(event) {
    this.setState({courseQuality: event.target.value});
  }

  handleProfQualityChange(event) {
      this.setState({profQuality: event.target.value});
    }

  async handleSubmit(event) {
    event.preventDefault();
    const { courses, courseHistory, interests, difficulty, courseQuality, profQuality } = this.state;
    const url = `http://localhost:8080/request/?courses=${courses}&courseHistory=${courseHistory}&interests={interests}&diff=${difficulty}&courseQual=${courseQuality}&profQual=${profQuality}`;
    const response = await fetch(url);
      response.json()
      .then(data => {
        if (!data.length) {
            this.setState({ noResults: true })
        }
        this.setState({ recs: data });
      });
  }

  renderRecs() {
    const { recs } = this.state;
    if (!recs.length) {
        if (this.state.noResults) {
            return "We didn't find any results!";
        } else {
            return null;
        }
    }
    return (
      <div className="table">
        <p> Here are some courses you might enjoy! </p>
        <table>
          <tr>
            <th>Course Code</th>
            <th>Course Name</th>
            <th>Course Quality</th>
            <th>Course Difficulty</th>
            <th>Professor Quality</th>
            <th>Course Description</th>
          </tr>
            {recs.map(rec =>
              <tr>
                <td>{rec.code}</td>
                <td className="name">{rec.name}</td>
                <td>{rec.courseQuality}</td>
                <td>{rec.courseDifficulty}</td>
                <td>{rec.profQuality}</td>
                <td className="desc">{rec.description}</td>
              </tr>
            )}
        </table>
      </div>
    );
  }

  render() {
    const { mounted } = this.state;
    if (!mounted) return null;
    const recs = this.renderRecs();
    const { classes } = this.props;
    return (
      <div className="form-root">
        <div className={classes.container}>
          <ValidatorForm onSubmit={this.handleSubmit}>
            <p className="info"> Please enter your courses in the following format: CIS-197 ACCT-101 </p>
            <div className="row">
              <label> Courses You Liked </label>
              <TextValidator
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.courses}
                onChange={this.handleCourseChange}
                validators={['required', 'matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)+$']}
                errorMessages={['This field is required', 'Please format course codes as shown above']}
                name="courses"
              />
            </div>
            <div className="row">
              <label> Courses You Took </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.courseHistory}
                onChange={this.handleCourseHistoryChange}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)+$']}
                errorMessages={['Please format course codes as shown above']}
                name="courseHistory"
              />
            </div>
            <div className="row">
              <label> Interests </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.interests}
                onChange={this.handleInterestsChange}
                name="interests"
              />
            </div>
            <div className="row">
              <label> Ideal Course Difficulty </label>
              <Select
                value={this.state.difficulty}
                onChange={this.handleDifficultyChange}
                input={
                  <OutlinedInput
                    name="difficulty"
                  />
                }
              >
                <MenuItem value={1}>0-1</MenuItem>
                <MenuItem value={2}>1-2</MenuItem>
                <MenuItem value={3}>2-3</MenuItem>
                <MenuItem value={4}>3-4</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Ideal Course Quality </label>
              <Select
                value={this.state.courseQuality}
                onChange={this.handleCourseQualityChange}
                input={
                  <OutlinedInput
                    name="quality"
                  />
                }
              >
                <MenuItem value={1}>0-1</MenuItem>
                <MenuItem value={2}>1-2</MenuItem>
                <MenuItem value={3}>2-3</MenuItem>
                <MenuItem value={4}>3-4</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Ideal Professor Quality </label>
              <Select
                value={this.state.profQuality}
                onChange={this.handleProfQualityChange}
                input={
                  <OutlinedInput
                    name="quality"
                  />
                }
              >
                <MenuItem value={1}>0-1</MenuItem>
                <MenuItem value={2}>1-2</MenuItem>
                <MenuItem value={3}>2-3</MenuItem>
                <MenuItem value={4}>3-4</MenuItem>
              </Select>
            </div>
            <Button
              className={classes.button}
              variant="outlined"
              type="submit"
            >
             Submit
            </Button>
          </ValidatorForm>
        </div>
        {recs}
      </div>
    );
  }
}

export default withStyles(styles)(Form);