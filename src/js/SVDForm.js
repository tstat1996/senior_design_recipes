import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import MenuItem from '@material-ui/core/MenuItem';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import { ValidatorForm, TextValidator} from 'react-material-ui-form-validator';
import '../css/SVDForm.css';

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

class SVDForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
        course1: 'CIS-110',
        course2: 'MATH-240',
        course3: 'JPAN-011',
        course4: 'ECON-001',
        course5: 'CHEM-101',
        rating1: '5',
        rating2: '5',
        rating3: '5',
        rating4: '5',
        rating5: '5',
        mounted: false,
        recs: [],
        noResults: false
    };

    this.handleCourse1Change = this.handleCourse1Change.bind(this);
    this.handleCourse2Change = this.handleCourse2Change.bind(this);
    this.handleCourse3Change = this.handleCourse3Change.bind(this);
    this.handleCourse4Change = this.handleCourse4Change.bind(this);
    this.handleCourse5Change = this.handleCourse5Change.bind(this);
    this.handleRating1Change = this.handleRating1Change.bind(this);
    this.handleRating2Change = this.handleRating2Change.bind(this);
    this.handleRating3Change = this.handleRating3Change.bind(this);
    this.handleRating4Change = this.handleRating4Change.bind(this);
    this.handleRating5Change = this.handleRating5Change.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.renderRecs = this.renderRecs.bind(this);
  }

  componentDidMount() {
    this.setState({ mounted: true });
  }

  handleCourse1Change(event) {
    this.setState({course1: event.target.value});
  }

  handleCourse2Change(event) {
    this.setState({course2: event.target.value});
  }

  handleCourse3Change(event) {
    this.setState({course3: event.target.value});
  }

  handleCourse4Change(event) {
    this.setState({course4: event.target.value});
  }

  handleCourse5Change(event) {
    this.setState({course5: event.target.value});
  }

  handleRating1Change(event) {
    this.setState({rating1: event.target.value});
  }

  handleRating2Change(event) {
    this.setState({rating2: event.target.value});
  }

  handleRating3Change(event) {
    this.setState({rating3: event.target.value});
  }

  handleRating4Change(event) {
    this.setState({rating4: event.target.value});
  }

  handleRating5Change(event) {
    this.setState({rating5: event.target.value});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { course1, course2, course3, course4, course5, rating1, rating2, rating3, rating4, rating5 } = this.state;
    const courses = course1 + ' ' + rating1 + ' ' + course2 + ' ' + rating2 + ' ' + course3 + ' ' + rating3 + ' ' + course4 + ' ' + rating4 + ' ' + course5 + ' ' + rating5;
    console.log(courses);
    const url = `http://localhost:8080/svd/?course1=${course1}&rating1=${rating1}&course2=${course2}&rating2=${rating2}&course3=${course3}&rating3=${rating3}&course4=${course4}&rating4=${rating4}&course5=${course5}&rating5=${rating5}`;
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
              <label> Course 1 </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.course1}
                onChange={this.handleCourse1Change}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)$']}
                errorMessages={['Please format course code as shown above']}
                name="course1"
              />
              <label> Rating </label>
              <Select
                value={this.state.rating1}
                onChange={this.handleRating1Change}
                input={
                  <OutlinedInput
                    name="rating1"
                  />
                }
              >
                <MenuItem value={1}>1</MenuItem>
                <MenuItem value={2}>2</MenuItem>
                <MenuItem value={3}>3</MenuItem>
                <MenuItem value={4}>4</MenuItem>
                <MenuItem value={5}>5</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Course 2 </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.course2}
                onChange={this.handleCourse2Change}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)$']}
                errorMessages={['Please format course code as shown above']}
                name="course2"
              />
              <label> Rating </label>
              <Select
                value={this.state.rating2}
                onChange={this.handleRating2Change}
                input={
                  <OutlinedInput
                    name="rating2"
                  />
                }
              >
                <MenuItem value={1}>1</MenuItem>
                <MenuItem value={2}>2</MenuItem>
                <MenuItem value={3}>3</MenuItem>
                <MenuItem value={4}>4</MenuItem>
                <MenuItem value={5}>5</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Course 3 </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.course3}
                onChange={this.handleCourse3Change}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)$']}
                errorMessages={['Please format course code as shown above']}
                name="course3"
              />
              <label> Rating </label>
              <Select
                value={this.state.rating3}
                onChange={this.handleRating3Change}
                input={
                  <OutlinedInput
                    name="rating3"
                  />
                }
              >
                <MenuItem value={1}>1</MenuItem>
                <MenuItem value={2}>2</MenuItem>
                <MenuItem value={3}>3</MenuItem>
                <MenuItem value={4}>4</MenuItem>
                <MenuItem value={5}>5</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Course 4 </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.course4}
                onChange={this.handleCourse4Change}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)$']}
                errorMessages={['Please format course code as shown above']}
                name="course4"
              />
              <label> Rating </label>
              <Select
                value={this.state.rating4}
                onChange={this.handleRating4Change}
                input={
                  <OutlinedInput
                    name="rating4"
                  />
                }
              >
                <MenuItem value={1}>1</MenuItem>
                <MenuItem value={2}>2</MenuItem>
                <MenuItem value={3}>3</MenuItem>
                <MenuItem value={4}>4</MenuItem>
                <MenuItem value={5}>5</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Course 5 </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.course5}
                onChange={this.handleCourse5Change}
                validators={['matchRegexp:^([A-Z]{3,4}\-[0-9]{3}\\s?)$']}
                errorMessages={['Please format course code as shown above']}
                name="course5"
              />
              <label> Rating </label>
              <Select
                value={this.state.rating5}
                onChange={this.handleRating5Change}
                input={
                  <OutlinedInput
                    name="rating5"
                  />
                }
              >
                <MenuItem value={1}>1</MenuItem>
                <MenuItem value={2}>2</MenuItem>
                <MenuItem value={3}>3</MenuItem>
                <MenuItem value={4}>4</MenuItem>
                <MenuItem value={5}>5</MenuItem>
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

export default withStyles(styles)(SVDForm);