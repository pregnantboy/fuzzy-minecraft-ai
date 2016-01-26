//
//  EditorViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "EditorViewController.h"
#import "DropDownTableViewCell.h"
#import "MainMenuViewController.h"
#import "ToggleTableViewCell.h"

@interface EditorViewController () {
    Rule *rule;
}

@end

@implementation EditorViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.9];
    [self.view addSubview:self.background positioned:NSWindowBelow relativeTo:nil];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.addButton];
    [MainMenuViewController addShadow:self.deleteButton];
    rule = [[Rule alloc] init];
    [self.backButton setAction:@selector(goToRulesListPage)];
    [self.addButton setAction: @selector(addNewRule)];
    [self.deleteButton setAction: @selector(removeRule)];
}

- (void)loadRule:(Rule *)ruleToLoad {
    rule = ruleToLoad;
}

#pragma mark - NSTableView delegates

- (BOOL)tableView:(NSTableView *)aTableView shouldSelectRow:(NSInteger)rowIndex {

    return YES;
}

- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return [rule getNumInputs];
}

- (CGFloat)tableView:(NSTableView *)tableView
         heightOfRow:(NSInteger)row {
    return 50;
}

- (NSView *)tableView:(NSTableView *)tableView
   viewForTableColumn:(NSTableColumn *)tableColumn
                  row:(NSInteger)row {
    
    // Retrieve to get the @"MyView" from the pool or,
    // if no version is available in the pool, load the Interface Builder version
    if ([tableColumn.identifier isEqualToString:@"first"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        if (row == 0) {
            [result setType:0];
        } else {
            [result setType:1];
            [result setString:[rule getOperatorAtIndex:row]];
        }
        return result;
    }
    
    else if ([tableColumn.identifier isEqualToString:@"second"]) {
        DropDownTableViewCell *result = (DropDownTableViewCell *) [tableView makeViewWithIdentifier:@"DropDownTableViewCell" owner:self];
        [result setType:2];
        NSString *condition = [rule getCondAtIndex:row];
        if (condition != nil) {
            [result setString:condition];
        }
        return result;
    }
    
    else if ([tableColumn.identifier isEqualToString:@"third"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        [result setType:3];
        NSString *eq = [rule getEqualityAtIndex:row];
        [result setString: eq];
        return result;
    }
    else{
        DropDownTableViewCell *result = (DropDownTableViewCell *) [tableView makeViewWithIdentifier:@"DropDownTableViewCell" owner:self];
        [result setType:4];
        NSString *condition = [rule getCondAtIndex:row];
        if (condition != nil) {
            [result enableValueButtonForCond:condition];
            NSString *value = [rule getValueAtIndex:row];
            if (value != nil) {
                [result setString: value];
            }
        }
        return result;
    }
}

#pragma mark - IBAction 

- (IBAction)goToRulesListPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"RulesListViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

- (IBAction)addNewRule {
    [rule createNewRuleInput];
    [self.inputTableView reloadData];
}

- (IBAction)removeRule {
    if ([rule getNumInputs] >1) {
        
        // do something here to remove selected row data
        [self.inputTableView reloadData];
    }
}



@end
